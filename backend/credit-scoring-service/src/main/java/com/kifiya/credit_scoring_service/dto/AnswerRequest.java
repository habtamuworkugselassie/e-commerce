package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class AnswerRequest {
    @NotNull
    @JsonProperty("customerId")
    private String customerId;

    @NotNull
    @JsonProperty("product_type")
    private String productType;

    @NotNull
    @JsonProperty("question_category")
    private String questionCategory;

    @JsonProperty("response")
    private List<Map<String, Object>> response = new ArrayList<>();
}

