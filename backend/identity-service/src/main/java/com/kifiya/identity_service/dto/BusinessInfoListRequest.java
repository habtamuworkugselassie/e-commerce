package com.kifiya.identity_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BusinessInfoListRequest {

    @NotEmpty
    @Valid
    private List<BusinessInfoRequest> businessinfo;
}

