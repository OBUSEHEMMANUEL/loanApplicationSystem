package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.register.LoginRequest;
import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.register.UpdateRegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;

public interface CustomerService {
    SucessResponse register(RegistrationRequest request);
    SucessResponse updateRegister(UpdateRegistrationRequest request);
    SucessResponse loanApplication(LoanRequest request);

    SucessResponse login(LoginRequest request);
}
