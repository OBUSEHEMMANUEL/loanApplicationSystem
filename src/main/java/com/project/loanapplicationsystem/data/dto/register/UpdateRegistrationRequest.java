package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRegistrationRequest {
    @NotBlank
    private String customerId;
    @NotBlank
    private String homeAddress;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    private String bankVerificationNumber;
    @NotBlank
    private String occupation;
    @NotNull
    private double annualIncome;
    @NotBlank
    private String maritalStatus;
    @NotBlank
    private String nationality;
}
