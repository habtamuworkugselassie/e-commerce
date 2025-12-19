package com.kifiya.loan_service.repository.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "loan_repayment_schedule")
public class LoanRepaymentSchedule {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private Integer installmentNumber;

    private LocalDate dueDate;

    private BigDecimal principal;

    private BigDecimal interest;

    private BigDecimal totalEmi;

    private Boolean paid = false;
}
