package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;

import java.util.List;

public interface LoanOfficerService {
SucessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest);
List<LoanApplication> viewLoanApplication();

Customer customerDetails(String customerId);
}
