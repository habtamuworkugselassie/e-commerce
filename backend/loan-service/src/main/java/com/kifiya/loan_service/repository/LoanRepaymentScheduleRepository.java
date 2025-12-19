package com.kifiya.loan_service.repository;

import com.kifiya.loan_service.repository.domain.LoanRepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepaymentScheduleRepository
        extends JpaRepository<LoanRepaymentSchedule, UUID> {

    List<LoanRepaymentSchedule> findByLoanId(UUID loanId);
}
