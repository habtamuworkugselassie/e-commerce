package com.kifiya.identity_service.dto;

import com.kifiya.identity_service.repository.domain.BusinessAssociationType;
import com.kifiya.identity_service.repository.domain.BusinessLevel;
import com.kifiya.identity_service.repository.domain.Region;
import com.kifiya.identity_service.repository.domain.SourceOfInitialCapital;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessInfoRequest {

    @NotNull
    private String customerId;

    @NotNull
    private BusinessAssociationType businessAssociationType;

    @NotNull
    private String businessSector;

    @NotNull
    private BusinessLevel businessLevel;

    @NotNull
    private Integer businessEstablishmentYear;

    @NotNull
    @Positive
    private Integer businessStartingNoOfEmployees;

    @NotNull
    @Positive
    private Integer businessCurrentNoOfEmployees;

    @NotNull
    @Positive
    private BigDecimal businessStartingCapital;

    @NotNull
    @Positive
    private BigDecimal businessCurrentCapital;

    @NotNull
    private SourceOfInitialCapital businessSourceOfInitialCapital;

    @NotNull
    @Positive
    private BigDecimal businessAnnualSales;

    @NotNull
    private BigDecimal businessAnnualProfit;

    @NotNull
    private Region businessRegion;

    @NotNull
    private String businessCity;

    @NotNull
    private String businessZoneOrSubCity;

    @NotNull
    private String businessWoreda;
}

