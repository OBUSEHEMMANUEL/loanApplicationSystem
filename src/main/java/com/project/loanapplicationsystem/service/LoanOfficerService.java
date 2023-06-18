package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.UUID;

public interface LoanOfficerService {
SucessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest);
List<LoanApplication> viewLoanApplication();

 Customer reviewCustomerDetails(String customerId);

 LoanApplication approveLoanApplication(UUID loanApplicationId) throws ResourceException;
}
