package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.UUID;

public interface LoanOfficerService {
SuccessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest);
List<LoanApplication> viewLoanApplication();

 Customer reviewCustomerDetails(String customerId);

 LoanApplication approveLoanApplication(String loanApplicationId) throws ResourceException;

 LoanApplication rejectedLoanApplication(String loanApplicationId) throws ResourceException;

    LoanApplication  closeLoanApplication(String  loanApplicationId) throws ResourceException;

    LoanAgreement generateLoanAgreement(LoanAgreementRequest request);
}
