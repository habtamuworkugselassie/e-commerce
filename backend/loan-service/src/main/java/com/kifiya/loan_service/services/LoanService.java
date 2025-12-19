package com.kifiya.loan_service.services;

import com.kifiya.loan_service.dto.LoanApplicationRequest;
import com.kifiya.loan_service.dto.LoanWebhookRequest;
import com.kifiya.loan_service.repository.LoanRepaymentScheduleRepository;
import com.kifiya.loan_service.repository.LoanRepository;
import com.kifiya.loan_service.repository.domain.Loan;
import com.kifiya.loan_service.repository.domain.LoanRepaymentSchedule;
import com.kifiya.loan_service.repository.domain.LoanStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanRepaymentScheduleRepository scheduleRepository;
    private final EmiCalculator emiCalculator;
    private final RepaymentScheduleGenerator scheduleGenerator;

    public LoanService(
            LoanRepository loanRepository,
            LoanRepaymentScheduleRepository scheduleRepository,
            EmiCalculator emiCalculator,
            RepaymentScheduleGenerator scheduleGenerator) {

        this.loanRepository = loanRepository;
        this.scheduleRepository = scheduleRepository;
        this.emiCalculator = emiCalculator;
        this.scheduleGenerator = scheduleGenerator;
    }

    public Loan applyForLoan(LoanApplicationRequest request) {

        // Invoice-based rule (example: 80% financing)
        BigDecimal maxAllowed =
                request.getInvoiceAmount().multiply(BigDecimal.valueOf(0.8));

        if (request.getLoanAmount().compareTo(maxAllowed) > 0) {
            throw new IllegalArgumentException("Loan exceeds invoice financing limit");
        }

        Loan loan = new Loan();
        loan.setCustomerId(request.getCustomerId());
        loan.setInvoiceId(request.getInvoiceId());
        loan.setInvoiceAmount(request.getInvoiceAmount());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setTenureInMonths(request.getTenureInMonths());
        loan.setStatus(LoanStatus.PENDING);
        loan.setApplicationDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Loan approveLoan(UUID loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

        BigDecimal emi = emiCalculator.calculateEmi(
                loan.getLoanAmount(),
                loan.getInterestRate(),
                loan.getTenureInMonths());

        List<LoanRepaymentSchedule> schedules =
                scheduleGenerator.generateSchedule(loan, emi);

        loan.setStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(LocalDate.now());

        scheduleRepository.saveAll(schedules);
        return loan;
    }

    public List<Loan> getLoans(UUID customerId) {

        return loanRepository.findAllByCustomerId(customerId);
    }

    /**
     * Processes loan details received via webhook.
     * Converts webhook request to loan application and applies the same business rules.
     */
    public Loan processWebhookLoan(LoanWebhookRequest request) {

        // Invoice-based rule (example: 80% financing)
        BigDecimal maxAllowed =
                request.getInvoiceAmount().multiply(BigDecimal.valueOf(0.8));

        if (request.getLoanAmount().compareTo(maxAllowed) > 0) {
            throw new IllegalArgumentException("Loan exceeds invoice financing limit");
        }

        Loan loan = new Loan();
        loan.setCustomerId(request.getCustomerId());
        loan.setInvoiceId(request.getInvoiceId());
        loan.setInvoiceAmount(request.getInvoiceAmount());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setInterestRate(request.getInterestRate());
        loan.setTenureInMonths(request.getTenureInMonths());
        loan.setStatus(LoanStatus.PENDING);
        loan.setApplicationDate(LocalDate.now());

        // Webhook-specific metadata could be stored here if needed
        // For now, we process it the same way as regular loan applications

        return loanRepository.save(loan);
    }
}
