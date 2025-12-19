package com.kifiya.credit_scoring_service.controller;

import com.kifiya.credit_scoring_service.dto.APIRequestDataX;
import com.kifiya.credit_scoring_service.dto.ClientCreate;
import com.kifiya.credit_scoring_service.dto.IntegrationPlatformSetup;
import com.kifiya.credit_scoring_service.dto.ModelCodeFarm;
import com.kifiya.credit_scoring_service.services.IntegrationPlatformService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class IntegrationPlatformController {

    private final IntegrationPlatformService integrationPlatformService;

    public IntegrationPlatformController(IntegrationPlatformService integrationPlatformService) {
        this.integrationPlatformService = integrationPlatformService;
    }

    @PostMapping("/Xscore")
    public ResponseEntity<Map<String, Object>> xscore(@Valid @RequestBody APIRequestDataX request) {
        Map<String, Object> result = integrationPlatformService.xscore(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/Xsetup/")
    public ResponseEntity<Map<String, Object>> populateIntegrationPlatform(@Valid @RequestBody IntegrationPlatformSetup request) {
        Map<String, Object> result = integrationPlatformService.populateIntegrationPlatform(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create_clients")
    public ResponseEntity<Map<String, Object>> createClient(@Valid @RequestBody ClientCreate request) {
        Map<String, Object> result = integrationPlatformService.createClient(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/model_farm_fetch")
    public ResponseEntity<Map<String, Object>> fetchModelFarm(@Valid @RequestBody ModelCodeFarm request) {
        Map<String, Object> result = integrationPlatformService.fetchModelFarm(request);
        return ResponseEntity.ok(result);
    }
}

