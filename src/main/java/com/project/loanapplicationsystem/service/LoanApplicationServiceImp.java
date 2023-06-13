package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class LoanApplicationServiceImp implements LoanApplicationService{

    private final LoanApplicationRepository repository;
    private CustomerService service;
    private final ModelMapper modelMapper;

    @Override
    public SucessResponse loanApplication(LoanRequest request) {
      Customer foundCustomer =  service.findById(request.getCustomerId()).orElseThrow(()->new RuntimeException("CUSTOMER NOT FOUND"));

      LoanApplication application =  modelMapper.map(request, LoanApplication.class);

      application.setDateApplied(LocalDateTime.now());
      application.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
     LoanApplication savedApplication =  repository.save(application);
        foundCustomer.getLoanApplication().add(savedApplication);

        return SucessResponse.builder()
                .message("Loan Application Submitted Successfully")
                .StatusCode(HttpStatus.ACCEPTED.value())
                .build();
    }
}
