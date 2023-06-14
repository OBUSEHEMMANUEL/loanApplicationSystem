package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.UUID;

public interface LoanApplicationService {
    SucessResponse loanApplication(LoanRequest request) throws ResourceException;

    LoanApplication acceptLoanApplication(UUID loanApplicationId) throws ResourceException;

    LoanApplication rejectedLoanApplication(UUID loanApplicationId) throws ResourceException;
    LoanApplication closeLoanApplication(UUID loanApplicationId) throws ResourceException;

    LoanApplication viewLoanApplicationStatus(UUID loanApplication) throws ResourceException;

    List<LoanApplication> viewLoanApplication();
}
