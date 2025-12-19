package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OverwritingRuleCreateNew {
    @NotNull
    @JsonProperty("credit_score")
    private Map<String, Object> creditScore;

    @NotNull
    @JsonProperty("duration_days")
    private List<Integer> durationDays = new ArrayList<>();

    @NotNull
    @JsonProperty("interest_rate")
    private List<Double> interestRate = new ArrayList<>();

    @NotNull
    @JsonProperty("loan_limit")
    private List<Integer> loanLimit = new ArrayList<>();
}

