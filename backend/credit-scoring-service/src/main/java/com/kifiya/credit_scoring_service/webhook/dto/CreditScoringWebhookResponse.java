package com.kifiya.credit_scoring_service.webhook.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CreditScoringWebhookResponse {
    private Map<String, Object> scoringResult;
    private String scoringType;
    private String message;
    private String webhookSource;
    private boolean processed;
}

