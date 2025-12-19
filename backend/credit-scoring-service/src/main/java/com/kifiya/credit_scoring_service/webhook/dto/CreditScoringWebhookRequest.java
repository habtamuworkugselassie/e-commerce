package com.kifiya.credit_scoring_service.webhook.dto;

import com.kifiya.credit_scoring_service.dto.AgriAPIRequestData;
import com.kifiya.credit_scoring_service.dto.APIRequestData;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreditScoringWebhookRequest {
    @Valid
    private APIRequestData scoreRequest;
    
    @Valid
    private AgriAPIRequestData agriScoreRequest;
    
    private String scoringType; // "standard", "agriculture", "repayment"
    
    // Optional webhook metadata
    private String sourceSystem;
    private String externalReferenceId;
    private String metadata;
}

