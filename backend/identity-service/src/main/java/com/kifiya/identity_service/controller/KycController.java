package com.kifiya.identity_service.controller;

import com.kifiya.identity_service.dto.KycListRequest;
import com.kifiya.identity_service.dto.KycRequest;
import com.kifiya.identity_service.repository.domain.Kyc;
import com.kifiya.identity_service.services.KycService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kyc")
public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Kyc>> fetchKyc(@PathVariable String customerId) {
        List<Kyc> kycList = kycService.getKycByCustomerId(customerId);
        return ResponseEntity.ok(kycList);
    }

    @PostMapping("/")
    public ResponseEntity<Void> registerKyc(@Valid @RequestBody KycListRequest request) {
        for (KycRequest kycRequest : request.getKyc()) {
            kycService.registerKyc(kycRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

