package com.kifiya.identity_service.webhook.dto;

import com.kifiya.identity_service.dto.BusinessInfoRequest;
import com.kifiya.identity_service.dto.KycRequest;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class IdentityWebhookRequest {
    @Valid
    private KycRequest kyc;
    
    @Valid
    private BusinessInfoRequest businessInfo;
    
    // Optional webhook metadata
    private String sourceSystem;
    private String externalReferenceId;
    private String metadata;
}

