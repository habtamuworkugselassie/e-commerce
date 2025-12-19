package com.kifiya.loan_service.controller;

import com.kifiya.loan_service.dto.LoanApplicationRequest;
import com.kifiya.loan_service.dto.LoanApplicationResponse;
import com.kifiya.loan_service.repository.domain.Loan;
import com.kifiya.loan_service.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> get(@RequestParam(value = "customerId") UUID customerId) {
        return ResponseEntity.ok(loanService.getLoans(customerId));
    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> apply(
            @Valid @RequestBody LoanApplicationRequest request) {

        Loan loan = loanService.applyForLoan(request);

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setLoanId(loan.getId());
        response.setStatus(loan.getStatus());
        response.setMessage("Loan application submitted successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{loanId}/approve")
    public ResponseEntity<Loan> approve(@PathVariable UUID loanId) {
        return ResponseEntity.ok(loanService.approveLoan(loanId));
    }
}
