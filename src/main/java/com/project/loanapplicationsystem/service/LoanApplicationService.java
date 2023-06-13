package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;

public interface LoanApplicationService {
    SucessResponse loanApplication(LoanRequest request);
}
