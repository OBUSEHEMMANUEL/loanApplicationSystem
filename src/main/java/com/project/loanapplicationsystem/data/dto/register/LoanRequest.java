package com.project.loanapplicationsystem.data.dto.register;

import lombok.Data;

@Data
public class LoanRequest {
    private Double loanAmount;
    private String purpose;
    private String repaymentPreferences;
}
