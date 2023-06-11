package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoginRequest;
import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.register.UpdateRegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
     private final ModelMapper modelMapper;

    @Override
    public SucessResponse register(RegistrationRequest request) {
   if(customerRepository.findByEmailAddress(request.getEmailAddress()).isPresent()){
        throw new RuntimeException("Customer Already Exist");
        }

   Customer customer =   modelMapper.map(request, Customer.class);
   String encodePassword = bcrypt(request.getPassword());
   customer.setPassword(encodePassword);
   customer.setDateRegistered(LocalDateTime.now());
   customerRepository.save(customer);

  return SucessResponse.builder()
           .message("Customer Registered Successfully")
           .StatusCode(HttpStatus.OK.value())
           .build();
    }

    @Override
    public SucessResponse updateRegister(UpdateRegistrationRequest request) {
        if(customerRepository.findById(request.getCustomerId()).isEmpty()){
           throw new RuntimeException("User Not Found");
       }
      Customer customer = modelMapper.map(request,Customer.class);
        customer.setDateUpdated(LocalDateTime.now());
        customerRepository.save(customer);

        return SucessResponse.builder()
                .message("Customer Registration Updated Successfully")
                .StatusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public SucessResponse login(LoginRequest request) {
        try{
     Customer foundUser =  customerRepository.findByEmailAddress(request.getEmailAddress()).orElseThrow(()->new RuntimeException("Invalid EmailAddress"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var matches = encoder.matches(request.getPassword(), foundUser.getPassword());

        if (matches) {
         return SucessResponse.builder()
                    .message("LOGIN SUCCESSFULLY")
                    .StatusCode(HttpStatus.OK.value())
                    .build();
        } else {
         return  SucessResponse.builder()
                    .message("INVALID EMAIL OR PASSWORD")
                    .StatusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }catch (RuntimeException e){
            return  SucessResponse.builder()
                    .message("ERROR"+ e.getMessage())
                    .StatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
    }
    }

    private String bcrypt(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);

    }


}
