package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCreate {
    @NotNull
    @JsonProperty("client_name")
    private String clientName;

    @NotNull
    @JsonProperty("client_code")
    private String clientCode;
}

