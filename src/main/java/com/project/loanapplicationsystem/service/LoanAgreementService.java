package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.model.LoanAgreement;

import java.util.UUID;

public interface LoanAgreementService {
      LoanAgreement generateLoanAgreement(LoanAgreementRequest request);
}
