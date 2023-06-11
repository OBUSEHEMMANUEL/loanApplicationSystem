package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRegistrationRequest {
    private String customerId;
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private String contactNumber;
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
