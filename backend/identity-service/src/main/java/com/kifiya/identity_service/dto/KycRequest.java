package com.kifiya.identity_service.dto;

import com.kifiya.identity_service.repository.domain.EducationLevel;
import com.kifiya.identity_service.repository.domain.Gender;
import com.kifiya.identity_service.repository.domain.MaritalStatus;
import com.kifiya.identity_service.repository.domain.Region;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KycRequest {

    @NotNull
    private String customerId;

    @NotNull
    private Gender customerGender;

    @NotNull
    private Integer customerAge;

    @NotNull
    private MaritalStatus customerMaritalStatus;

    @NotNull
    private EducationLevel customerEducationLevel;

    @NotNull
    private Region customerRegion;

    @NotNull
    private String customerCity;

    @NotNull
    private String customerZoneOrSubCity;

    @NotNull
    private String customerWoreda;

    private Integer salaryBankAccountNumber;
    private String companyName;
}

