package com.kifiya.credit_scoring_service.webhook;

import com.kifiya.credit_scoring_service.dto.AgriAPIRequestData;
import com.kifiya.credit_scoring_service.dto.APIRequestData;
import com.kifiya.credit_scoring_service.services.ScoringService;
import com.kifiya.credit_scoring_service.webhook.dto.CreditScoringWebhookRequest;
import com.kifiya.credit_scoring_service.webhook.dto.CreditScoringWebhookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook/credit-scoring")
public class CreditScoringWebhookController {

    private final ScoringService scoringService;

    public CreditScoringWebhookController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping
    public ResponseEntity<CreditScoringWebhookResponse> receiveScoringRequest(
            @Valid @RequestBody CreditScoringWebhookRequest request,
            @RequestHeader(value = "X-Webhook-Source", required = false) String webhookSource) {

        CreditScoringWebhookResponse response = new CreditScoringWebhookResponse();
        response.setWebhookSource(webhookSource);
        response.setProcessed(false);

        try {
            Map<String, Object> result;
            String scoringType = request.getScoringType() != null 
                    ? request.getScoringType() 
                    : "standard";

            // Determine which scoring to perform based on request data
            if (request.getAgriScoreRequest() != null) {
                result = scoringService.agriScore(request.getAgriScoreRequest());
                scoringType = "agriculture";
            } else if (request.getScoreRequest() != null) {
                // Check if it's repayment score based on scoringType
                if ("repayment".equalsIgnoreCase(scoringType)) {
                    result = scoringService.getRepaymentScore(request.getScoreRequest());
                } else {
                    result = scoringService.score(request.getScoreRequest());
                }
            } else {
                response.setMessage("No valid scoring request data provided");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            response.setScoringResult(result);
            response.setScoringType(scoringType);
            response.setProcessed(true);
            response.setMessage("Scoring request processed successfully");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("Error processing scoring request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

