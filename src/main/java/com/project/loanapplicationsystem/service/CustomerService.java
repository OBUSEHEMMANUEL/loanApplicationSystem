package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.CustomerLoginRequest;
import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.register.UpdateRegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;

import java.util.Optional;

public interface CustomerService {
    SucessResponse register(RegistrationRequest request);
    SucessResponse updateRegister(UpdateRegistrationRequest request);


    SucessResponse login(CustomerLoginRequest request);
 Optional<Customer> findById(String id);
}
