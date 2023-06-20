package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.LoanResponse;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.UUID;

public interface LoanOfficerService {
SuccessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest);
List<LoanResponse> viewAllLoanApplication();

 Customer reviewCustomerDetails(String customerId);

    SuccessResponse approveLoanApplication(String loanApplicationId) throws ResourceException;

    SuccessResponse rejectedLoanApplication(String loanApplicationId) throws ResourceException;

    SuccessResponse  closeLoanApplication(String  loanApplicationId) throws ResourceException;

    LoanAgreement generateLoanAgreement(LoanAgreementRequest request);
}
