package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class APIRequestData {
    @NotNull
    @Valid
    @JsonProperty("APIrequest_data")
    private APIRequestIdentifier APIrequestData;
}

