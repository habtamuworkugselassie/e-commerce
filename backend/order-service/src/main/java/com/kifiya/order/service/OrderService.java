package com.kifiya.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.order.models.CustomerSegment;
import com.kifiya.order.models.Order;
import com.kifiya.order.models.OrderItem;
import com.kifiya.order.models.OrderStatus;
import com.kifiya.order.repository.OrderRepository;
import com.kifiya.order.webhook.dto.OrderItemWebhookDto;
import com.kifiya.order.webhook.dto.OrderWebhookRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Processes order details received via webhook.
     * Creates and persists Order with OrderItems.
     */
    public Order processWebhookOrder(OrderWebhookRequest request) {

        UUID orderId = UUID.randomUUID();

        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId(request.customerId());
        order.setCustomerSegment(CustomerSegment.valueOf(request.customerSegment().toUpperCase()));
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalOriginalPrice(request.totalOriginalPrice());
        order.setTotalFinalPrice(request.totalFinalPrice());
        order.setTotalDiscount(request.totalDiscount());

        // Create OrderItems from webhook request
        for (OrderItemWebhookDto itemDto : request.items()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDto.productId());
            orderItem.setProductName(itemDto.productName());
            orderItem.setProductCategory(itemDto.productCategory());
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setUnitPrice(itemDto.unitPrice());
            orderItem.setOriginalPrice(itemDto.originalPrice());
            orderItem.setFinalPrice(itemDto.finalPrice());
            orderItem.setDiscountAmount(itemDto.discountAmount());

            // Serialize applied promotions to JSON
            try {
                String promotionsJson = objectMapper.writeValueAsString(itemDto.appliedPromotions());
                orderItem.setAppliedPromotions(promotionsJson);
            } catch (JsonProcessingException e) {
                // If serialization fails, store empty array
                orderItem.setAppliedPromotions("[]");
            }

            order.addItem(orderItem);
        }

        return orderRepository.save(order);
    }
}

