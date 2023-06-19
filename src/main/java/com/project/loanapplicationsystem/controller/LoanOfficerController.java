package com.project.loanapplicationsystem.controller;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.exception.ResourceException;
import com.project.loanapplicationsystem.service.LoanOfficerService;
import com.project.loanapplicationsystem.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/loanOfficer")
public class LoanOfficerController {
    @Autowired
    private LoanOfficerService loanOfficerService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loanOfficerLogin(@RequestBody LoanOfficerLoginRequest loginRequest){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.loanOfficerLogin(loginRequest))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/ViewLoanApplication")
    public ResponseEntity<ApiResponse> viewLoanApplication(){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.viewLoanApplication())
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/reviewCustomerDetails/{customerId}")
    public ResponseEntity<ApiResponse>  ReviewCustomerDetails(@PathVariable String customerId){
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.reviewCustomerDetails(customerId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/approve/{loanApplicationId}")
    public ResponseEntity<ApiResponse>  approveLoanApplication(@PathVariable UUID loanApplicationId) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.approveLoanApplication(loanApplicationId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/close/{loanApplicationId}")
    public ResponseEntity<ApiResponse>  closeLoanApplication(@PathVariable UUID loanApplicationId) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.closeLoanApplication(loanApplicationId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/reject/{loanApplicationId}")
    public ResponseEntity<ApiResponse>  rejectLoanApplication(@PathVariable UUID loanApplicationId) throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.rejectedLoanApplication(loanApplicationId))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/generateLoanAgreement/{loanApplicationId}")
    public ResponseEntity<ApiResponse>  generateLoanAgreement(LoanAgreementRequest request)  throws ResourceException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(loanOfficerService.generateLoanAgreement(request))
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
