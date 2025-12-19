package com.kifiya.credit_scoring_service.controller;

import com.kifiya.credit_scoring_service.dto.CreateAllRulesForNew;
import com.kifiya.credit_scoring_service.services.ProductConfigurationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class ProductConfigurationController {

    private final ProductConfigurationService productConfigurationService;

    public ProductConfigurationController(ProductConfigurationService productConfigurationService) {
        this.productConfigurationService = productConfigurationService;
    }

    @PostMapping("/setup")
    public ResponseEntity<Map<String, Object>> configure(@Valid @RequestBody CreateAllRulesForNew request) {
        Map<String, Object> result = productConfigurationService.configure(request);
        return ResponseEntity.ok(result);
    }
}

