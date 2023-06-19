package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanAgreementRequest;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.repostory.LoanAgreementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoanAgreementServiceImpl.class)
class LoanAgreementServiceImplTest {
@MockBean
    private  LoanAgreementRepository loanAgreementRepository;
@MockBean
    private LoanApplicationService loanApplicationService;
@MockBean
    private  ModelMapper modelMapper;
    @Autowired
    private LoanAgreementService loanAgreementService;


@BeforeEach
void setup(){
    MockitoAnnotations.openMocks(this);
}

    @Test
    void testGenerateLoanAgreement_Success() {
        // Arrange
        LoanAgreementRequest request = new LoanAgreementRequest();
        request.setLoanApplicationId("123");

        LoanApplication foundLoanApplication = new LoanApplication();
        LoanAgreement loanAgreement = new LoanAgreement();
        LoanAgreement savedLoanAgreement = new LoanAgreement();

        when(loanApplicationService.findLoanApplicationById("123")).thenReturn(Optional.of(foundLoanApplication));
        when(modelMapper.map(request, LoanAgreement.class)).thenReturn(loanAgreement);
        when(loanAgreementRepository.save(loanAgreement)).thenReturn(savedLoanAgreement);

        // Act
        LoanAgreement result = loanAgreementService.generateLoanAgreement(request);

        // Assert
        assertNotNull(result);
        assertSame(savedLoanAgreement, result);
        verify(loanApplicationService, times(1)).findLoanApplicationById("123");
        verify(modelMapper, times(1)).map(request, LoanAgreement.class);
        verify(loanAgreementRepository, times(1)).save(loanAgreement);
    }

    @Test
    void testGenerateLoanAgreement_InvalidLoanApplication() {
        LoanAgreementRequest request = new LoanAgreementRequest();
        request.setLoanApplicationId("123");

        when(loanApplicationService.findLoanApplicationById("123")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> loanAgreementService.generateLoanAgreement(request));

        verifyNoInteractions(loanAgreementRepository);
        verifyNoInteractions(modelMapper);
    }
}