package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.LoanResponse;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanApplicationService {
//    SucessResponse loanApplication(LoanRequest request) throws ResourceException;

   SuccessResponse loanApplication(LoanRequest request) throws ResourceException;

    SuccessResponse acceptLoanApplication(String loanApplicationId) throws ResourceException;

    SuccessResponse rejectedLoanApplication(String loanApplicationId) throws ResourceException;
    SuccessResponse closeLoanApplication(String loanApplicationId) throws ResourceException;

    LoanApplication viewLoanApplicationStatus(String loanApplication) throws ResourceException;

    List<LoanResponse> viewLoanAllApplication();

    LoanApplication saveLoanApplication(LoanApplication loanApplication);

    Optional<LoanApplication> findLoanApplicationById(String id);

}
