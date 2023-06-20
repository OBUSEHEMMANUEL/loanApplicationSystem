package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.LoanResponse;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import jdk.jshell.execution.FailOverExecutionControlProvider;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.project.loanapplicationsystem.data.model.enums.ApplicationStatus.*;

@AllArgsConstructor
@Service
public class LoanApplicationServiceImp implements LoanApplicationService {

    private final LoanApplicationRepository loanRepository;

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Override
    public SuccessResponse loanApplication(LoanRequest request) throws ResourceException {
  Customer foundCustomer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new ResourceException("CUSTOMER NOT FOUND"));
        LoanApplication application = modelMapper.map(request, LoanApplication.class);

        application.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
        application.setDateApplied(LocalDateTime.now());
       application.setCustomer(foundCustomer);
 LoanApplication  savedLoanApplication = loanRepository.save(application);
        return   SuccessResponse.builder()
                .statusCode(HttpStatus.ACCEPTED.value())
                .applicationStatus(savedLoanApplication.getApplicationStatus())
                .message("LOAN IN_PROGRESS")
                .build();

    }

    @Override
    public SuccessResponse acceptLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == APPROVED) {
            throw new ResourceException("Loan has already been Accepted");
        }
        loanApplication.setApplicationStatus(APPROVED);
        loanApplication.setDateAccepted(LocalDateTime.now());
        LoanApplication  savedLoanApplication =  loanRepository.save(loanApplication);
        return  SuccessResponse.builder()
                .statusCode(HttpStatus.ACCEPTED.value())
                .applicationStatus(savedLoanApplication.getApplicationStatus())
                .message("LOAN ACCEPTED")
                .build();
    }

    @Override
    public SuccessResponse rejectedLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == REJECTED) {
            throw new ResourceException("Loan has already been Rejected");
        }
        loanApplication.setApplicationStatus(REJECTED);
        loanApplication.setDateRejected(LocalDateTime.now());
        LoanApplication  savedLoanApplication =  loanRepository.save(loanApplication);
        return   SuccessResponse.builder()
                .applicationStatus(savedLoanApplication.getApplicationStatus())
                .statusCode(HttpStatus.ACCEPTED.value())
                .message("LOAN REJECTED")
                .build();
    }

    @Override
    public SuccessResponse closeLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == CLOSED) {
            throw new ResourceException("Loan has already been Closed");
        }
        loanApplication.setApplicationStatus(CLOSED);
        loanApplication.setDateClosed(LocalDateTime.now());
        LoanApplication  savedLoanApplication=  loanRepository.save(loanApplication);
        return   SuccessResponse.builder()
                .applicationStatus(savedLoanApplication.getApplicationStatus())
                .statusCode(HttpStatus.ACCEPTED.value())
                .message("LOAN CLOSED")
                .build();
    }
    @Override
    public LoanApplication viewLoanApplicationStatus(String loanApplication) throws ResourceException {
        return loanRepository.findById(loanApplication)
                .orElseThrow(()->new ResourceException("id not found"));
    }
    @Override
    public List<LoanResponse> viewLoanAllApplication() {
        List<LoanApplication> loanApplications =  loanRepository.findAll();
     return loanApplications.stream().map(loanApplication-> modelMapper.map(loanApplication, LoanResponse.class)).collect(Collectors.toList());
    }
    @Override
    public LoanApplication saveLoanApplication(LoanApplication loanApplication){
      return   loanRepository.save(loanApplication);
    }
    @Override
    public Optional<LoanApplication> findLoanApplicationById(String id){
       return loanRepository.findById(id);
    }

}
