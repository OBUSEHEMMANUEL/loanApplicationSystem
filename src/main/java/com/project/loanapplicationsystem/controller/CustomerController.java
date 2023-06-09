package com.project.loanapplicationsystem.controller;

import com.project.loanapplicationsystem.data.dto.register.*;
import com.project.loanapplicationsystem.exception.ResourceException;
import com.project.loanapplicationsystem.service.CustomerService;
import com.project.loanapplicationsystem.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
   private CustomerService customerService;
@PostMapping("/register")
    public ResponseEntity<ApiResponse> registration(@RequestBody @Valid RegistrationRequest request) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.register(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
@PatchMapping("/updateRegistrstion")
    public ResponseEntity<ApiResponse> updateRegistration(@RequestBody UpdateRegistrationRequest request){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.updateRegister(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
@PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid CustomerLoginRequest request){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.login(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/confirmToken")
    public ResponseEntity<ApiResponse> confirmToken(@RequestBody ConfirmTokenRequest request) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.confirmToken(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/forgottenPassword")
    public ResponseEntity<ApiResponse> forgottenPassword(@RequestBody ForgottenPasswordRequest request) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.forgottenPassword(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PatchMapping("/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid SetPasswordRequest request) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.resetPassword(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/loanApplication")
    public ResponseEntity<ApiResponse> loanApplication(@RequestBody LoanRequest request) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.loanApplication(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/viewLoanApplication/{loanApplicationId}/{customerId}")
    public ResponseEntity<ApiResponse> viewLoanApplication(@PathVariable String  loanApplicationId,@PathVariable String  customerId) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.viewLoanApplicationStatus( loanApplicationId, customerId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/viewLoanAgreement/{loanApplicationId}")
public ResponseEntity<ApiResponse> viewLoanAgreement(@PathVariable String  loanApplicationId) throws ResourceException {
        com.project.loanapplicationsystem.utils.ApiResponse response = com.project.loanapplicationsystem.utils.ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.getLoanAgreement(loanApplicationId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
