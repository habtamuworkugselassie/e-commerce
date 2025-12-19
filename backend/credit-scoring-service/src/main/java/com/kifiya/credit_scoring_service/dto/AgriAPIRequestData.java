package com.kifiya.credit_scoring_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgriAPIRequestData {
    @NotNull
    @Valid
    private AgriAPIRequestIdentifier APIrequestData;
}

