package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanRequest {

    private String customerId;
   @PositiveOrZero
    private double loanAmount;
    @NotBlank
    private String purpose;
    @NotBlank
    private String repaymentPreferences;
}
