package com.kifiya.identity_service.webhook;

import com.kifiya.identity_service.repository.domain.BusinessInfo;
import com.kifiya.identity_service.repository.domain.Kyc;
import com.kifiya.identity_service.services.BusinessInfoService;
import com.kifiya.identity_service.services.KycService;
import com.kifiya.identity_service.webhook.dto.IdentityWebhookRequest;
import com.kifiya.identity_service.webhook.dto.IdentityWebhookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/webhook/identity")
public class IdentityWebhookController {

    private final KycService kycService;
    private final BusinessInfoService businessInfoService;

    public IdentityWebhookController(KycService kycService, BusinessInfoService businessInfoService) {
        this.kycService = kycService;
        this.businessInfoService = businessInfoService;
    }

    @PostMapping
    public ResponseEntity<IdentityWebhookResponse> receiveIdentityData(
            @Valid @RequestBody IdentityWebhookRequest request,
            @RequestHeader(value = "X-Webhook-Source", required = false) String webhookSource) {

        // Validate that at least one of kyc or businessInfo is provided
        if (request.getKyc() == null && request.getBusinessInfo() == null) {
            IdentityWebhookResponse errorResponse = new IdentityWebhookResponse();
            errorResponse.setMessage("At least one of 'kyc' or 'businessInfo' must be provided");
            errorResponse.setWebhookSource(webhookSource);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        IdentityWebhookResponse response = new IdentityWebhookResponse();
        response.setWebhookSource(webhookSource);
        response.setKycProcessed(false);
        response.setBusinessInfoProcessed(false);

        // Process KYC if provided
        if (request.getKyc() != null) {
            try {
                Kyc kyc = kycService.registerKyc(request.getKyc());
                response.setKycId(kyc.getId());
                response.setKycProcessed(true);
            } catch (Exception e) {
                response.setMessage("Error processing KYC: " + e.getMessage());
            }
        }

        // Process BusinessInfo if provided
        if (request.getBusinessInfo() != null) {
            try {
                BusinessInfo businessInfo = businessInfoService.registerBusinessInfo(request.getBusinessInfo());
                response.setBusinessInfoId(businessInfo.getId());
                response.setBusinessInfoProcessed(true);
            } catch (Exception e) {
                String existingMessage = response.getMessage() != null ? response.getMessage() + "; " : "";
                response.setMessage(existingMessage + "Error processing BusinessInfo: " + e.getMessage());
            }
        }

        // Set success message if both processed successfully
        if (response.isKycProcessed() && response.isBusinessInfoProcessed()) {
            response.setMessage("KYC and BusinessInfo received and processed successfully");
        } else if (response.isKycProcessed()) {
            response.setMessage("KYC received and processed successfully");
        } else if (response.isBusinessInfoProcessed()) {
            response.setMessage("BusinessInfo received and processed successfully");
        } else {
            response.setMessage("No valid data to process");
        }

        HttpStatus status = (response.isKycProcessed() || response.isBusinessInfoProcessed()) 
                ? HttpStatus.CREATED 
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }
}

