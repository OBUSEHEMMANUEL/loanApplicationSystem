package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class LoanApplicationImp implements LoanApplicationService{

    private final LoanApplicationRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public SucessResponse loanApplication(LoanRequest request) {

      LoanApplication application =  modelMapper.map(request, LoanApplication.class);

      application.setDateApplied(LocalDateTime.now());
        repository.save(application);


        return null;
    }
}
