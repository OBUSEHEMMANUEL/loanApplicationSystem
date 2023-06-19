package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.repostory.LoanAgreementRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanAgreementServiceImpl implements LoanAgreementService{

    private final LoanAgreementRepository loanAgreementRepository;

 private final LoanApplicationService loanApplicationService;
 private final ModelMapper modelMapper;

    @Override
    public LoanAgreement generateLoanAgreement(LoanAgreementRequest request) {
  LoanApplication foundLoanApplication=   loanApplicationService.
          findLoanApplicationById(String.valueOf(request.getLoanApplicationId())).orElseThrow(()->new RuntimeException("Loan Application Not Found"));
  LoanAgreement loanAgreement =   modelMapper.map(request,LoanAgreement.class);

   LoanAgreement savedLoanAgreement =  loanAgreementRepository.save(loanAgreement);
 foundLoanApplication.setLoanAgreement(savedLoanAgreement);
 loanApplicationService.saveLoanApplication(foundLoanApplication);
 return savedLoanAgreement;
    }


}
