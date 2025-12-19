package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScoreWeightCreateNew {
    @NotNull
    @JsonProperty("business_score")
    private Double businessScore;

    @NotNull
    @JsonProperty("transaction_score")
    private Double transactionScore;

    @NotNull
    @JsonProperty("psycometric_score")
    private Double psycometricScore;

    @NotNull
    @JsonProperty("risk_score")
    private Double riskScore;

    @NotNull
    @JsonProperty("message_score")
    private Double messageScore;
}

