package com.kifiya.credit_scoring_service.controller;

import com.kifiya.credit_scoring_service.dto.AgriAPIRequestData;
import com.kifiya.credit_scoring_service.dto.APIRequestData;
import com.kifiya.credit_scoring_service.services.ScoringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class ScoringController {

    private final ScoringService scoringService;

    public ScoringController(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping("/score")
    public ResponseEntity<Map<String, Object>> score(@Valid @RequestBody APIRequestData request) {
        Map<String, Object> result = scoringService.score(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/agri_score")
    public ResponseEntity<Map<String, Object>> agriScore(@Valid @RequestBody AgriAPIRequestData request) {
        Map<String, Object> result = scoringService.agriScore(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/get_repayment_score")
    public ResponseEntity<Map<String, Object>> getRepaymentScore(@Valid @RequestBody APIRequestData request) {
        Map<String, Object> result = scoringService.getRepaymentScore(request);
        return ResponseEntity.ok(result);
    }
}

