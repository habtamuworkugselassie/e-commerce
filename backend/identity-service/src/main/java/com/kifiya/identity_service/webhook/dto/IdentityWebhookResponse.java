package com.kifiya.identity_service.webhook.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class IdentityWebhookResponse {
    private UUID kycId;
    private UUID businessInfoId;
    private String message;
    private String webhookSource;
    private boolean kycProcessed;
    private boolean businessInfoProcessed;
}

