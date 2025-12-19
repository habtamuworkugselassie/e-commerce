package com.kifiya.identity_service.controller;

import com.kifiya.identity_service.dto.BusinessInfoListRequest;
import com.kifiya.identity_service.dto.BusinessInfoRequest;
import com.kifiya.identity_service.repository.domain.BusinessInfo;
import com.kifiya.identity_service.services.BusinessInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businessinfo")
public class BusinessInfoController {

    private final BusinessInfoService businessInfoService;

    public BusinessInfoController(BusinessInfoService businessInfoService) {
        this.businessInfoService = businessInfoService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<BusinessInfo>> fetchBusinessInfo(@PathVariable String customerId) {
        List<BusinessInfo> businessInfoList = businessInfoService.getBusinessInfoByCustomerId(customerId);
        return ResponseEntity.ok(businessInfoList);
    }

    @PostMapping("/")
    public ResponseEntity<Void> registerBusinessInfo(@Valid @RequestBody BusinessInfoListRequest request) {
        for (BusinessInfoRequest businessInfoRequest : request.getBusinessinfo()) {
            businessInfoService.registerBusinessInfo(businessInfoRequest);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

