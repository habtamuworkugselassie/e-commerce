package com.kifiya.credit_scoring_service.controller;

import com.kifiya.credit_scoring_service.dto.AnswerRequest;
import com.kifiya.credit_scoring_service.dto.QuestionRequest;
import com.kifiya.credit_scoring_service.services.PsychometricService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class PsychometricController {

    private final PsychometricService psychometricService;

    public PsychometricController(PsychometricService psychometricService) {
        this.psychometricService = psychometricService;
    }

    @PostMapping("/getPsycometricQuestions")
    public ResponseEntity<Map<String, Object>> getQuestions(@Valid @RequestBody QuestionRequest request) {
        Map<String, Object> result = psychometricService.getQuestions(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getPsycometricAnswers")
    public ResponseEntity<Map<String, Object>> getAnswers(@Valid @RequestBody AnswerRequest request) {
        Map<String, Object> result = psychometricService.getAnswers(request);
        return ResponseEntity.ok(result);
    }
}

