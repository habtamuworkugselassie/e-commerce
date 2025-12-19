package com.kifiya.loan_service.services;

import com.kifiya.loan_service.repository.domain.Loan;
import com.kifiya.loan_service.repository.domain.LoanRepaymentSchedule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepaymentScheduleGenerator {

    public List<LoanRepaymentSchedule> generateSchedule(
            Loan loan,
            BigDecimal emi) {

        List<LoanRepaymentSchedule> schedules = new ArrayList<>();

        BigDecimal outstanding = loan.getLoanAmount();
        BigDecimal monthlyRate = loan.getInterestRate()
                .divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        for (int i = 1; i <= loan.getTenureInMonths(); i++) {

            BigDecimal interest =
                    outstanding.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);

            BigDecimal principal =
                    emi.subtract(interest);

            outstanding = outstanding.subtract(principal);

            LoanRepaymentSchedule schedule = new LoanRepaymentSchedule();
            schedule.setLoan(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(LocalDate.now().plusMonths(i));
            schedule.setPrincipal(principal);
            schedule.setInterest(interest);
            schedule.setTotalEmi(emi);

            schedules.add(schedule);
        }

        return schedules;
    }
}
