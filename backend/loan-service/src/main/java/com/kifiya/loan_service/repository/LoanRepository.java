package com.kifiya.loan_service.repository;

import com.kifiya.loan_service.repository.domain.Loan;
import com.kifiya.loan_service.repository.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByCustomerId(UUID customerId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findAllByCustomerId(UUID customerId);
}
