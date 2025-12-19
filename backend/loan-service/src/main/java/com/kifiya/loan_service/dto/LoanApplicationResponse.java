package com.kifiya.loan_service.dto;

import com.kifiya.loan_service.repository.domain.LoanStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class LoanApplicationResponse {

    private UUID loanId;
    private LoanStatus status;
    private String message;
}
