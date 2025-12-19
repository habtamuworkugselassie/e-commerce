package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class APIRequestIdentifier {
    @NotNull
    @JsonProperty("source_bank")
    private String sourceBank;

    @NotNull
    @JsonProperty("customerId")
    private String customerId;

    @NotNull
    @JsonProperty("loan_type")
    private String loanType;

    @NotNull
    @JsonProperty("loan_subtype")
    private String loanSubtype;
}

