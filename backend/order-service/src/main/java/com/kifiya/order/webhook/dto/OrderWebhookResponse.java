package com.kifiya.order.webhook.dto;

import com.kifiya.order.models.OrderStatus;

import java.util.UUID;

public record OrderWebhookResponse(
        UUID orderId,
        OrderStatus status,
        String message,
        String webhookSource
) {
}

