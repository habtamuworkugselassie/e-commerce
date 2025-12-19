package com.kifiya.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.order.config.PromotionAppConfig;
import com.kifiya.order.controller.domains.*;
import com.kifiya.order.models.CustomerSegment;
import com.kifiya.order.models.IdempotencyKeyRecord;
import com.kifiya.order.models.Order;
import com.kifiya.order.models.OrderItem;
import com.kifiya.order.models.OrderStatus;
import com.kifiya.order.models.Product;
import com.kifiya.order.exceptions.BadRequestException;
import com.kifiya.order.exceptions.ConflictException;
import com.kifiya.order.exceptions.ResourceNotFoundException;
import com.kifiya.order.repository.IdempotencyKeyRecordRepository;
import com.kifiya.order.repository.OrderRepository;
import com.kifiya.order.repository.ProductRepository;
import com.kifiya.order.service.domains.LineItem;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductRepository productRepository;
    private final IdempotencyKeyRecordRepository idempotencyRepository;
    private final OrderRepository orderRepository;
    private final PromotionEngineService promotionEngine;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartService(ProductRepository productRepository,
                       IdempotencyKeyRecordRepository idempotencyRepository,
                       OrderRepository orderRepository,
                       PromotionEngineService promotionEngine) {
        this.productRepository = productRepository;
        this.idempotencyRepository = idempotencyRepository;
        this.orderRepository = orderRepository;
        this.promotionEngine = promotionEngine;
    }

    /**
     * Calculates the price breakdown for a cart request.
     */
    public CartQuoteResponse quoteCart(CartQuoteRequest request) {

        List<UUID> productIds = request.items().stream().map(CartItemDto::productId).toList();

        Map<UUID, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        for (CartItemDto item : request.items()) {
            if (!productMap.containsKey(item.productId())) {
                throw new ResourceNotFoundException("Product not found with ID: " + item.productId());
            }
            if (productMap.get(item.productId()).getStock() < item.qty()) {

                throw new ConflictException("Product " + item.productId() + " has insufficient stock for quote.");
            }
        }

        List<LineItem> lineItems = request.items().stream()
                .map(item -> new LineItem(item, productMap.get(item.productId())))
                .toList();

        CustomerSegment customerSegment = parseCustomerSegment(request.customerSegment());

        promotionEngine.applyPromotions(productMap, lineItems, customerSegment);

        List<PriceBreakdownDto> breakdown = lineItems.stream()
                .map(item -> {
                    BigDecimal originalPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getRequestedQty()));
                    BigDecimal finalPrice = item.getCurrentPrice();
                    BigDecimal discountAmount = originalPrice.subtract(finalPrice);

                    return new PriceBreakdownDto(
                            item.getProduct().getName() + " (x" + item.getRequestedQty() + ")",
                            originalPrice.setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE),
                            finalPrice.setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE),
                            discountAmount.setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE),
                            item.getRequestedQty(),
                            item.getAppliedPromotions().stream()
                                    .sorted(Comparator.comparingInt(item.getAppliedPromotions()::indexOf).reversed())
                                    .map(ap -> new AppliedPromotionDto("Promotion", ap.name(), ap.description()))
                                    .toList()
                    );
                })
                .toList();

        BigDecimal totalOriginalPrice = breakdown.stream()
                .map(PriceBreakdownDto::originalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalFinalPrice = breakdown.stream()
                .map(PriceBreakdownDto::finalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = totalOriginalPrice.subtract(totalFinalPrice).setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE);

        return new CartQuoteResponse(
                breakdown,
                totalOriginalPrice.setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE),
                totalFinalPrice.setScale(2, PromotionAppConfig.MONEY_ROUNDING_MODE),
                totalDiscount
        );
    }

    /**
     * Validates stock, reserves inventory, and handles idempotency atomically.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CartConfirmResponse confirmCart(CartQuoteRequest request, String idempotencyKey) {

        UUID orderId = UUID.randomUUID();

        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            return idempotencyRepository.findByKeyId(idempotencyKey)
                    .map(keyRecord -> new CartConfirmResponse(
                            keyRecord.getOrderId(),
                            quoteCart(request).totalFinalPrice(),
                            "Order already confirmed with this Idempotency-Key."
                    ))
                    .orElseGet(() -> processConfirmation(request, orderId, idempotencyKey));
        }

        return processConfirmation(request, orderId, null);
    }

    private CartConfirmResponse processConfirmation(CartQuoteRequest request, UUID orderId, String idempotencyKey) {

        CartQuoteResponse quote = quoteCart(request);
        BigDecimal finalPrice = quote.totalFinalPrice();

        // Get products and create line items for order persistence
        List<UUID> productIds = request.items().stream().map(CartItemDto::productId).toList();
        Map<UUID, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<LineItem> lineItems = request.items().stream()
                .map(item -> new LineItem(item, productMap.get(item.productId())))
                .toList();

        CustomerSegment customerSegment = parseCustomerSegment(request.customerSegment());
        promotionEngine.applyPromotions(productMap, lineItems, customerSegment);

        // Reserve stock
        for (CartItemDto item : request.items()) {
            Product product = productMap.get(item.productId());

            int quantityToReserve = item.qty();

            if (product.getStock() < quantityToReserve) {
                throw new ConflictException("Insufficient stock for product: " + product.getName() + ". Available: " + product.getStock());
            }

            product.setStock(product.getStock() - quantityToReserve);

            try {
                productRepository.save(product);
            } catch (OptimisticLockingFailureException e) {
                throw new ConflictException("Stock reservation failed due to concurrent update for product: " + product.getName());
            }
        }

        // Create and persist Order
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerSegment(customerSegment);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalOriginalPrice(quote.totalOriginalPrice());
        order.setTotalFinalPrice(quote.totalFinalPrice());
        order.setTotalDiscount(quote.totalDiscount());
        order.setIdempotencyKey(idempotencyKey);

        // Create OrderItems from line items
        for (int i = 0; i < lineItems.size(); i++) {
            LineItem lineItem = lineItems.get(i);
            PriceBreakdownDto breakdown = quote.lineItems().get(i);
            Product product = lineItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductCategory(product.getCategory());
            orderItem.setQuantity(lineItem.getRequestedQty());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setOriginalPrice(breakdown.originalPrice());
            orderItem.setFinalPrice(breakdown.finalPrice());
            orderItem.setDiscountAmount(breakdown.discountAmount());

            // Serialize applied promotions to JSON
            try {
                String promotionsJson = objectMapper.writeValueAsString(breakdown.appliedPromotions());
                orderItem.setAppliedPromotions(promotionsJson);
            } catch (JsonProcessingException e) {
                // If serialization fails, store empty string
                orderItem.setAppliedPromotions("[]");
            }

            order.addItem(orderItem);
        }

        orderRepository.save(order);

        if (idempotencyKey != null) {
            idempotencyRepository.save(
                    IdempotencyKeyRecord.builder()
                            .keyId(idempotencyKey).orderId(orderId)
                            .build());
        }

        return new CartConfirmResponse(orderId, finalPrice, "Order confirmed successfully.");
    }

    private CustomerSegment parseCustomerSegment(String segment) {
        try {
            return CustomerSegment.valueOf(segment.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid customer segment: '" + segment + "'. Must be one of: " +
                    java.util.Arrays.toString(CustomerSegment.values()));
        }
    }
}
