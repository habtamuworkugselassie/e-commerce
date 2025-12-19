package com.kifiya.order.controller;


import com.kifiya.order.controller.domains.PromotionRequest;
import com.kifiya.order.models.Product;
import com.kifiya.order.models.Promotion;
import com.kifiya.order.models.PromotionType;
import com.kifiya.order.repository.PromotionRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/promotions")
class PromotionController {
    private final PromotionRepository promotionRepository;

    @GetMapping
    public ResponseEntity<List<Promotion>> getPromotion() {
        List<Promotion> list = promotionRepository.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody PromotionRequest request) {

        Promotion promotion = new Promotion();
        promotion.setName(request.name());
        promotion.setType(PromotionType.valueOf(request.type()));
        promotion.setConfig(request.config());
        promotion.setTargetSegments(request.targetSegments());
        Promotion saved = promotionRepository.save(promotion);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
