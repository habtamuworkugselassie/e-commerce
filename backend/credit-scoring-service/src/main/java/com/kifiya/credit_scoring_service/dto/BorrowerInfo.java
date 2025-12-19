package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowerInfo {
    @NotNull
    @JsonProperty("business_region")
    private String businessRegion;

    @NotNull
    @JsonProperty("business_sector")
    private String businessSector;

    @NotNull
    @JsonProperty("business_source_capital")
    private String businessSourceCapital;

    @NotNull
    @JsonProperty("business_number_of_employees")
    private Integer businessNumberOfEmployees;

    @NotNull
    @JsonProperty("customer_age")
    private Integer customerAge;

    @NotNull
    @JsonProperty("customer_gender")
    private String customerGender;

    @NotNull
    @JsonProperty("customer_martial_status")
    private String customerMartialStatus;

    @NotNull
    @JsonProperty("customer_education_level")
    private String customerEducationLevel;

    @NotNull
    @JsonProperty("loan_product_type")
    private String loanProductType;

    @NotNull
    @JsonProperty("credit_score")
    private Integer creditScore;
}

