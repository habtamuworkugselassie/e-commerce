package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class IntegrationPlatformSetup {
    @NotNull
    @JsonProperty("version")
    private Integer version;

    @NotNull
    @JsonProperty("modified_by")
    private String modifiedBy;

    @NotNull
    @JsonProperty("client_code")
    private String clientCode;

    @NotNull
    @JsonProperty("sub_product_code")
    private String subProductCode;

    @NotNull
    @JsonProperty("cust_product_name")
    private String custProductName;

    @NotNull
    @JsonProperty("cust_product_code")
    private String custProductCode;

    @NotNull
    @JsonProperty("model_names_list")
    private List<String> modelNamesList = new ArrayList<>();

    @NotNull
    @JsonProperty("model_codes_list")
    private List<String> modelCodesList = new ArrayList<>();

    @NotNull
    @JsonProperty("overwriting_rule")
    private Map<String, Object> overwritingRule;

    @NotNull
    @JsonProperty("score_weight")
    private Map<String, Object> scoreWeight;
}

