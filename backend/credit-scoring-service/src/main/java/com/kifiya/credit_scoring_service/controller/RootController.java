package com.kifiya.credit_scoring_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> startRoot() {
        return ResponseEntity.ok(Map.of("status", "Credit Scoring Service is running"));
    }
}

