package com.project.loanapplicationsystem.data.dto.register;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class LoanAgreementRequest {
    private String loanApplicationId;
    private String loanTerms;
    private String repaymentSchedule;
    private LocalDate agreementDate;
    private BigDecimal interestRate;
}
