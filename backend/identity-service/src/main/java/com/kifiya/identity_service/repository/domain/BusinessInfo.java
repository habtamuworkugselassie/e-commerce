package com.kifiya.identity_service.repository.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "business_info")
public class BusinessInfo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessAssociationType businessAssociationType;

    @Column(nullable = false)
    private String businessSector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessLevel businessLevel;

    @Column(nullable = false)
    private Integer businessEstablishmentYear;

    @Column(nullable = false)
    private Integer businessStartingNoOfEmployees;

    @Column(nullable = false)
    private Integer businessCurrentNoOfEmployees;

    @Column(nullable = false)
    private BigDecimal businessStartingCapital;

    @Column(nullable = false)
    private BigDecimal businessCurrentCapital;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceOfInitialCapital businessSourceOfInitialCapital;

    @Column(nullable = false)
    private BigDecimal businessAnnualSales;

    @Column(nullable = false)
    private BigDecimal businessAnnualProfit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region businessRegion;

    @Column(nullable = false)
    private String businessCity;

    @Column(nullable = false)
    private String businessZoneOrSubCity;

    @Column(nullable = false)
    private String businessWoreda;
}

