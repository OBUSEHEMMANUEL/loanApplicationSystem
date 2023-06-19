package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.exception.ResourceException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanApplicationService {
//    SucessResponse loanApplication(LoanRequest request) throws ResourceException;

    LoanApplication loanApplication(LoanRequest request) throws ResourceException;

    LoanApplication acceptLoanApplication(String loanApplicationId) throws ResourceException;

    LoanApplication rejectedLoanApplication(String loanApplicationId) throws ResourceException;
    LoanApplication closeLoanApplication(String loanApplicationId) throws ResourceException;

    LoanApplication viewLoanApplicationStatus(String loanApplication) throws ResourceException;

    List<LoanApplication> viewLoanAllApplication();

    LoanApplication saveLoanApplication(LoanApplication loanApplication);

    Optional<LoanApplication> findLoanApplicationById(String id);

}
