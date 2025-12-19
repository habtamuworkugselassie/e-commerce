package com.kifiya.loan_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanApplicationRequest {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID invoiceId;

    @NotNull
    @Positive
    private BigDecimal loanAmount;

    @NotNull
    @Positive
    private BigDecimal invoiceAmount;

    @NotNull
    @Positive
    private BigDecimal interestRate;

    @NotNull
    @Min(1)
    private Integer tenureInMonths;
}
