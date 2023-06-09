package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;

public interface CustomerService {
    SucessResponse register(RegistrationRequest request);
}
