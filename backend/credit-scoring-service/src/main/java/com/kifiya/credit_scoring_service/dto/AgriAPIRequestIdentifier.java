package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AgriAPIRequestIdentifier {
    @NotNull
    @JsonProperty("source_bank")
    private String sourceBank;

    @NotNull
    @JsonProperty("customerId")
    private String customerId;

    @NotNull
    @JsonProperty("loan_type")
    private String loanType;

    @JsonProperty("sectors")
    private List<String> sectors = new ArrayList<>();
}

