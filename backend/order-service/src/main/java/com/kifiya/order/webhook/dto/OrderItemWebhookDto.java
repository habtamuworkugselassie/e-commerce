package com.kifiya.order.webhook.dto;

import com.kifiya.order.models.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderItemWebhookDto(
        @NotNull UUID productId,
        String productName,
        ProductCategory productCategory,
        @Min(1) @NotNull Integer quantity,
        @NotNull @Positive BigDecimal unitPrice,
        @NotNull @Positive BigDecimal originalPrice,
        @NotNull @Positive BigDecimal finalPrice,
        @NotNull BigDecimal discountAmount,
        List<String> appliedPromotions
) {
}

