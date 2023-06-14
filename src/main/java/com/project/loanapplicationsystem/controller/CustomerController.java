package com.project.loanapplicationsystem.controller;

import com.project.loanapplicationsystem.data.dto.register.CustomerLoginRequest;
import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.register.UpdateRegistrationRequest;
import com.project.loanapplicationsystem.service.CustomerService;
import com.project.loanapplicationsystem.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
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
@PostMapping("/regiser")
    public ResponseEntity<ApiResponse> registration(@RequestBody RegistrationRequest request, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.register(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
@PatchMapping("/updateRegistrstion")
    public ResponseEntity<ApiResponse> updateRegistration(@RequestBody UpdateRegistrationRequest request, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.updateRegister(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
@PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody CustomerLoginRequest request, HttpServletRequest httpServletRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(customerService.login(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
