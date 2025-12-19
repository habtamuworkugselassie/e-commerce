package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelCodeFarm {
    @NotNull
    @JsonProperty("sub_product_code")
    private String subProductCode;
}

