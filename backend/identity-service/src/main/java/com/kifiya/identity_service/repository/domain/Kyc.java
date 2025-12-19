package com.kifiya.identity_service.repository.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "kyc")
public class Kyc {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender customerGender;

    @Column(nullable = false)
    private Integer customerAge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaritalStatus customerMaritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationLevel customerEducationLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region customerRegion;

    @Column(nullable = false)
    private String customerCity;

    @Column(nullable = false)
    private String customerZoneOrSubCity;

    @Column(nullable = false)
    private String customerWoreda;

    private Integer salaryBankAccountNumber;
    private String companyName;
}

