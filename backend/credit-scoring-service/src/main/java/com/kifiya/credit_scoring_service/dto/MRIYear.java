package com.kifiya.credit_scoring_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MRIYear {
    @NotNull
    private Integer year;
}

