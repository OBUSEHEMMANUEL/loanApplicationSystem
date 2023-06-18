package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.LoanOfficer;
import com.project.loanapplicationsystem.data.repostory.LoanOfficerRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
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
    private final BCryptPasswordEncoder encoder;

   private void createLoanOfficer() {

String encodedPassword =  encoder.encode("Admin");
        LoanOfficer loanOfficer = LoanOfficer.builder()
                .userName("Admin")
                .password(encodedPassword)
                .build();

        loanOfficerRepository.save(loanOfficer);
    }


    @Override
    public SucessResponse loanOfficerLogin(LoanOfficerLoginRequest loginRequest) {
        try {
           LoanOfficer foundUser = loanOfficerRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() -> new RuntimeException("Officer not Registered"));
            var matches = encoder.matches(loginRequest.getPassword(), foundUser.getPassword());

            if (matches) {
                return SucessResponse.builder()
                        .message("LOGIN SUCCESSFUL")
                        .StatusCode(HttpStatus.ACCEPTED.value())
                        .build();
            } else {
                return SucessResponse.builder()
                        .message("INVALID PASSWORD")
                        .StatusCode(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (RuntimeException e) {
            return SucessResponse.builder()
                    .message("ERROR: " + e.getMessage())
                    .StatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @Override
    public List<LoanApplication> viewLoanApplication() {
       return loanApplicationService.viewLoanApplication();
    }

    @Override
    public Customer reviewCustomerDetails(String customerId) {

   Customer foundCustomer =  customerService.findById(customerId)
           .orElseThrow(()-> new ResourceAccessException("Customer Not Found"));
     return foundCustomer;
    }

    @Override
    public LoanApplication approveLoanApplication(UUID loanApplicationId) throws ResourceException {

     return loanApplicationService.acceptLoanApplication(loanApplicationId);

    }


}
