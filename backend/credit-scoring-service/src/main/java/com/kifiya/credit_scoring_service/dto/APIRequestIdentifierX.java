package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class APIRequestIdentifierX {
    @NotNull
    @JsonProperty("client_code")
    private String clientCode;

    @NotNull
    @JsonProperty("customerId")
    private String customerId;

    @NotNull
    @JsonProperty("cust_product_code")
    private String custProductCode;

    @NotNull
    @JsonProperty("sub_product_code")
    private String subProductCode;
}

