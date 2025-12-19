package com.kifiya.credit_scoring_service.controller;

import com.kifiya.credit_scoring_service.dto.BorrowerInfo;
import com.kifiya.credit_scoring_service.dto.LocationInfo;
import com.kifiya.credit_scoring_service.dto.MRIYear;
import com.kifiya.credit_scoring_service.services.RiskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @PostMapping("/request_portfolio_risk")
    public ResponseEntity<Map<String, Object>> getPortfolioRisk(@Valid @RequestBody BorrowerInfo request) {
        Map<String, Object> result = riskService.getPortfolioRisk(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/request_macroeconomic_risk")
    public ResponseEntity<Map<String, Object>> getMacroRisk(@Valid @RequestBody MRIYear request) {
        Map<String, Object> result = riskService.getMacroRisk(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/NDVI_soil_scoring")
    public ResponseEntity<Map<String, Object>> ndviSoilScoring(@Valid @RequestBody LocationInfo request) {
        Map<String, Object> result = riskService.ndviSoilScoring(request);
        return ResponseEntity.ok(result);
    }
}

