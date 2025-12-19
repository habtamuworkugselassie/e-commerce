package com.kifiya.order.webhook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderWebhookRequest(
        UUID customerId,
        @NotBlank String customerSegment,
        @NotEmpty List<OrderItemWebhookDto> items,
        @NotNull BigDecimal totalOriginalPrice,
        @NotNull BigDecimal totalFinalPrice,
        @NotNull BigDecimal totalDiscount,
        String sourceSystem,
        String externalReferenceId,
        String metadata
) {
}

