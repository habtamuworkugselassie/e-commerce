package com.kifiya.loan_service.webhook;

import com.kifiya.loan_service.dto.LoanWebhookRequest;
import com.kifiya.loan_service.dto.LoanWebhookResponse;
import com.kifiya.loan_service.repository.domain.Loan;
import com.kifiya.loan_service.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/loans")
public class LoanWebhookController {

    private final LoanService loanService;

    public LoanWebhookController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanWebhookResponse> receiveLoanDetails(
            @Valid @RequestBody LoanWebhookRequest request,
            @RequestHeader(value = "X-Webhook-Source", required = false) String webhookSource) {

        Loan loan = loanService.processWebhookLoan(request);

        LoanWebhookResponse response = new LoanWebhookResponse();
        response.setLoanId(loan.getId());
        response.setStatus(loan.getStatus());
        response.setMessage("Loan details received and processed successfully");
        response.setWebhookSource(webhookSource);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

