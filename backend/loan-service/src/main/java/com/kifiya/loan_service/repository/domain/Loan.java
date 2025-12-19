package com.kifiya.loan_service.repository.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private UUID invoiceId;

    @Column(nullable = false)
    private BigDecimal invoiceAmount;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private BigDecimal interestRate; // annual %

    @Column(nullable = false)
    private Integer tenureInMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    private LocalDate applicationDate;
    private LocalDate approvalDate;
    // getters & setters
}
