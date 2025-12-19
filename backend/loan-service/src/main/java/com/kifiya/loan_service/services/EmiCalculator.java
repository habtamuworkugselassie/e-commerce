package com.kifiya.loan_service.services;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EmiCalculator {

    public BigDecimal calculateEmi(
            BigDecimal principal,
            BigDecimal annualInterestRate,
            int tenureMonths) {

        BigDecimal monthlyRate = annualInterestRate
                .divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRPowerN =
                monthlyRate.add(BigDecimal.ONE).pow(tenureMonths);

        BigDecimal numerator =
                principal.multiply(monthlyRate).multiply(onePlusRPowerN);

        BigDecimal denominator =
                onePlusRPowerN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
