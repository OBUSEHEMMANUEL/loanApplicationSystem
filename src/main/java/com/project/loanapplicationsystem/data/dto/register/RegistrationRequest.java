package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RegistrationRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private String emailAddress;
    @NotBlank
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

    private LocalDateTime dateCollected;
}
