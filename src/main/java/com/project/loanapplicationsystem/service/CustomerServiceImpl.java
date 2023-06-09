package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public SucessResponse register(RegistrationRequest request) {
   if(customerRepository.findByEmailAddress(request.getEmailAddress())){
        throw new RuntimeException("Customer Already Exist");
        }

   Customer customer =   modelMapper.map(request, Customer.class);
   String encodePassword = passwordEncoder.encode(request.getPassword());
   customer.setPassword(encodePassword);
   customer.setDateRegistered(LocalDateTime.now());
   customerRepository.save(customer);

  return SucessResponse.builder()
           .message("Customer Registered Successfully")
           .StatusCode(HttpStatus.OK.value())
           .build();
    }

}
