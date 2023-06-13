package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanRequest {
    private String customerId;
    @NotBlank
    private Double loanAmount;
    @NotBlank
    private String purpose;
    @NotBlank
    private String repaymentPreferences;
}
