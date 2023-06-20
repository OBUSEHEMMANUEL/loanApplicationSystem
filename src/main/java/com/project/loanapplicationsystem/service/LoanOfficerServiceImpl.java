package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.LoanResponse;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.LoanOfficer;
import com.project.loanapplicationsystem.data.repostory.LoanOfficerRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanOfficerServiceImpl implements LoanOfficerService{
private  final LoanOfficerRepository loanOfficerRepository;

private final LoanApplicationService loanApplicationService;

private final CustomerService customerService;

private final LoanAgreementService loanAgreementService;
    private final BCryptPasswordEncoder encoder;
@PostConstruct
   private void createLoanOfficer() {

String encodedPassword =  encoder.encode("Admin");
        LoanOfficer loanOfficer = LoanOfficer.builder()
                .userName("Admin")
                .password(encodedPassword)
                .build();
        loanOfficerRepository.save(loanOfficer);
    }


    @Override
    public SuccessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest) {
        try {
           LoanOfficer foundUser = loanOfficerRepository.findByUserNameIgnoreCase(loginRequest.getUserName()).orElseThrow(() -> new RuntimeException("Officer not Registered"));
            var matches = encoder.matches(loginRequest.getPassword(), foundUser.getPassword());

            if (matches) {
                return SuccessResponse.builder()
                        .message("LOGIN SUCCESSFUL")
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .build();
            } else {
                return SuccessResponse.builder()
                        .message("INVALID PASSWORD")
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (RuntimeException e) {
            return SuccessResponse.builder()
                    .message("ERROR: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @Override
    public List<LoanResponse> viewAllLoanApplication() {
       return loanApplicationService.viewLoanAllApplication();
    }

    @Override
    public Customer reviewCustomerDetails(String customerId) {

   Customer foundCustomer =  customerService.findById(customerId)
           .orElseThrow(()-> new ResourceAccessException("Customer Not Found"));
     return foundCustomer;
    }

    @Override
    public SuccessResponse approveLoanApplication(String loanApplicationId) throws ResourceException {

     return loanApplicationService.acceptLoanApplication(loanApplicationId);

    }

    @Override
    public SuccessResponse rejectedLoanApplication(String  loanApplicationId) throws ResourceException {
        return loanApplicationService.rejectedLoanApplication(loanApplicationId);
    }
    @Override
    public SuccessResponse  closeLoanApplication(String  loanApplicationId) throws ResourceException {
       return loanApplicationService.closeLoanApplication(loanApplicationId);
    }
    @Override
    public LoanAgreement generateLoanAgreement(LoanAgreementRequest request) {

      return loanAgreementService.generateLoanAgreement(request);
    }


}
