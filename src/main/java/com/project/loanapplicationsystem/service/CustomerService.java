package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.*;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.exception.ResourceException;
import jakarta.mail.MessagingException;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    SuccessResponse register(RegistrationRequest request) throws ResourceException;
    SuccessResponse updateRegister(UpdateRegistrationRequest request);


    SuccessResponse login(CustomerLoginRequest request);
 Optional<Customer> findById(String id);

   LoanApplication loanApplication(LoanRequest request) throws ResourceException;

    ApplicationStatus viewLoanApplicationStatus(String loanApplicationId, String customerId) throws ResourceException;

    LoanAgreement getLoanAgreement(String loanApplicationId) throws ResourceException;

    String confirmToken(ConfirmTokenRequest confirmToken) throws ResourceException;

    String resetPassword(SetPasswordRequest passwordRequest) throws  ResourceException;

    String forgottenPassword(ForgottenPasswordRequest request) throws ResourceException;

}
