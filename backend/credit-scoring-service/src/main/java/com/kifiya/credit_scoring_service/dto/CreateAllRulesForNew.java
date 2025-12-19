package com.kifiya.credit_scoring_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAllRulesForNew {
    @NotNull
    @JsonProperty("bp_product_id")
    private String bpProductId;

    @NotNull
    @JsonProperty("client")
    private String client;

    @NotNull
    @JsonProperty("version")
    private Integer version;

    @NotNull
    @JsonProperty("modified_by")
    private String modifiedBy;

    @NotNull
    @Valid
    @JsonProperty("overwriting_rule")
    private OverwritingRuleCreateNew overwritingRule;

    @NotNull
    @Valid
    @JsonProperty("score_weight")
    private ScoreWeightCreateNew scoreWeight;

    @NotNull
    @Valid
    @JsonProperty("parameter_range")
    private ParameterRangeCreateNew parameterRange;
}

