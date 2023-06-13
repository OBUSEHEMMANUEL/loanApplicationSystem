package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoanApplicationServiceImp.class)
class LoanApplicationServiceImpTest {
@MockBean
    private  LoanApplicationRepository loanRepository;
@MockBean
    private  ModelMapper modelMapper;
@Autowired
private LoanApplicationService loanApplicationService;

private LoanRequest request;

@BeforeEach
void setUp(){
    request = LoanRequest.builder()
            .loanAmount(2000.00)
            .purpose("School fees")
            .repaymentPreferences("Monthly")
            .build();

}
    @Test
    void testThatUserCanApplyForLoan() {
  LoanApplication mappedApplication = modelMapper.map(request,LoanApplication.class);
  when(modelMapper.map(request,LoanApplication.class)).thenReturn(mappedApplication);
  when(loanRepository.save(any())).thenReturn(any());
 SucessResponse response = loanApplicationService.loanApplication(request);
       assertNotNull(response);
       assertEquals(HttpStatus.ACCEPTED.value(),response.getStatusCode());
}
}