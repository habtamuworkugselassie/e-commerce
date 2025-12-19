package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotNull
    @JsonProperty("customer_id")
    private String customerId;

    @NotNull
    @JsonProperty("product_type")
    private String productType;
}

