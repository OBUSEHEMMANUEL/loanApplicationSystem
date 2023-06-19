package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import jdk.jshell.execution.FailOverExecutionControlProvider;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.loanapplicationsystem.data.model.enums.ApplicationStatus.*;

@AllArgsConstructor
@Service
public class LoanApplicationServiceImp implements LoanApplicationService {

    private final LoanApplicationRepository loanRepository;

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Override
    public LoanApplication loanApplication(LoanRequest request) throws ResourceException {
  Customer foundCustomer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new ResourceException("CUSTOMER NOT FOUND"));
        LoanApplication application = modelMapper.map(request, LoanApplication.class);

        application.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
        application.setDateApplied(LocalDateTime.now());
       application.setCustomer(foundCustomer);
       return loanRepository.save(application);

    }

    @Override
    public LoanApplication acceptLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == APPROVED) {
            throw new ResourceException("Loan has already been Accepted");
        }
        loanApplication.setApplicationStatus(APPROVED);
        loanApplication.setDateAccepted(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }

    @Override
    public LoanApplication rejectedLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == REJECTED) {
            throw new ResourceException("Loan has already been Rejected");
        }
        loanApplication.setApplicationStatus(REJECTED);
        loanApplication.setDateRejected(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }

    @Override
    public LoanApplication closeLoanApplication(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(loanApplicationId).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == CLOSED) {
            throw new ResourceException("Loan has already been Closed");
        }
        loanApplication.setApplicationStatus(CLOSED);
        loanApplication.setDateClosed(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }
    @Override
    public LoanApplication viewLoanApplicationStatus(String loanApplication) throws ResourceException {
        return loanRepository.findById(loanApplication)
                .orElseThrow(()->new ResourceException("id not found"));
    }
    @Override
    public List<LoanApplication> viewLoanAllApplication() {
        return loanRepository.findAll();
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
