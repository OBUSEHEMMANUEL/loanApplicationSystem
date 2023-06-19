package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoanApplicationServiceImp.class)
class LoanApplicationServiceImpTest {
    @MockBean
    private LoanApplicationRepository loanRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private CustomerRepository customerRepository;
    @Autowired
    private LoanApplicationService loanApplicationService;
    private LoanRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = LoanRequest.builder()
                .customerId("12345de")
                .loanAmount(2000.00)
                .purpose("School fees")
                .repaymentPreferences("Monthly")
                .build();

    }

    @Test
    void testThatUserCanApplyForLoan() throws ResourceException {

        LoanApplication mappedApplication = new ModelMapper().map(request, LoanApplication.class);

        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.of(new Customer()));
        when(modelMapper.map(request, LoanApplication.class)).thenReturn(mappedApplication);
        when(loanRepository.save(any())).thenReturn(mappedApplication);
       LoanApplication response = loanApplicationService.loanApplication(request);
        verify(loanRepository).save(mappedApplication);
        assertNotNull(response);
        assertEquals(ApplicationStatus.IN_PROGRESS, response.getApplicationStatus());
        verify(loanRepository, timeout(1)).save(any(LoanApplication.class));
    }

    @Test
    void testLoanApplicationWithMissingId() {
        LoanRequest request1 = LoanRequest.builder()
                .customerId(null)
                .loanAmount(2000.00)
                .purpose("School fees")
                .repaymentPreferences("Monthly")
                .build();
        when(customerRepository.findById(request1.getCustomerId())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> loanApplicationService.loanApplication(request1));
    }
    @Test
    void testLoanApplicationWithInvalidId() {
        LoanRequest request1 = LoanRequest.builder()
                .customerId("Invalid")
                .loanAmount(2000.00)
                .purpose("School fees")
                .repaymentPreferences("Monthly")
                .build();
        when(customerRepository.findById(request1.getCustomerId())).thenReturn(Optional.empty());
        assertThrows(ResourceException.class, () -> loanApplicationService.loanApplication(request1));
    }

    @Test
    void testAcceptedLoanApplicationStatusIsSuccessful() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));
        when(loanRepository.save(any())).thenReturn(loanApplication);
        LoanApplication loanService = loanApplicationService.acceptLoanApplication(String.valueOf(loanApplicationId));
        assertEquals(ApplicationStatus.APPROVED,loanService.getApplicationStatus());
    }
    @Test
    void testAcceptLoanApplicationNotFound(){
        UUID loanApplicationId = UUID.randomUUID();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.empty());
        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.acceptLoanApplication(String.valueOf(loanApplicationId)));
   assertEquals("Application not found", exception.getMessage());
    }
    @Test
    void testAcceptLoanApplicationIsAlreadyAccepted(){
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.APPROVED);
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.acceptLoanApplication(String.valueOf(loanApplicationId)));

        assertEquals("Loan has already been Accepted", exception.getMessage());
    }
    @Test
    void testThatAcceptLoanApplicationDateAcceptedIsSetToCurrentDateTime() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();

        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(new LoanApplication()));
        when(loanRepository.save(any())).thenReturn(any());
        loanApplicationService.acceptLoanApplication(String.valueOf(loanApplicationId));
        ArgumentCaptor<LoanApplication> loanCaptor = ArgumentCaptor.forClass(LoanApplication.class);
            verify(loanRepository).save(loanCaptor.capture());
      LoanApplication updateLoanApplication = loanCaptor.getValue();
            LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    LocalDateTime actualDateTime =    updateLoanApplication.getDateAccepted().truncatedTo(ChronoUnit.SECONDS);
    assertEquals(currentDateTime,actualDateTime);
    }

    @Test
    void testRejectedLoanApplicationStatusIsSuccessful() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));
        when(loanRepository.save(any())).thenReturn(loanApplication);
        LoanApplication loanService = loanApplicationService.rejectedLoanApplication(String.valueOf(loanApplicationId));
        assertEquals(ApplicationStatus.REJECTED,loanService.getApplicationStatus());
    }
    @Test
    void testRejectedLoanApplicationNotFound(){
        UUID loanApplicationId = UUID.randomUUID();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.empty());
        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.rejectedLoanApplication(String.valueOf(loanApplicationId)));
        assertEquals("Application not found", exception.getMessage());
    }
    @Test
    void testRejectedLoanApplicationIsAlreadyRejected(){
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.REJECTED);
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.rejectedLoanApplication(String.valueOf(loanApplicationId)));

        assertEquals("Loan has already been Rejected", exception.getMessage());
    }
    @Test
    void testThatRejectedLoanApplicationDateRejectedIsSetToCurrentDateTime() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();

        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(new LoanApplication()));
        when(loanRepository.save(any())).thenReturn(any());
        loanApplicationService.rejectedLoanApplication(String.valueOf(loanApplicationId));
        ArgumentCaptor<LoanApplication> loanCaptor = ArgumentCaptor.forClass(LoanApplication.class);
        verify(loanRepository).save(loanCaptor.capture());
        LoanApplication updateLoanApplication = loanCaptor.getValue();
        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime actualDateTime =    updateLoanApplication.getDateRejected().truncatedTo(ChronoUnit.SECONDS);
        assertEquals(currentDateTime,actualDateTime);
    }
    @Test
    void testClosedLoanApplicationStatusIsSuccessful() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));
        when(loanRepository.save(any())).thenReturn(loanApplication);
        LoanApplication loanService = loanApplicationService.closeLoanApplication(String.valueOf(loanApplicationId));
        assertEquals(ApplicationStatus.CLOSED,loanService.getApplicationStatus());
    }
    @Test
    void testClosedLoanApplicationNotFound(){
        UUID loanApplicationId = UUID.randomUUID();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.empty());
        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.closeLoanApplication(String.valueOf(loanApplicationId)));
        assertEquals("Application not found", exception.getMessage());
    }
    @Test
    void testCloseLoanApplicationIsAlreadyClosed(){
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.CLOSED);
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        ResourceException exception =  assertThrows(ResourceException.class, ()->loanApplicationService.closeLoanApplication(String.valueOf(loanApplicationId)));

        assertEquals("Loan has already been Closed", exception.getMessage());
    }
    @Test
    void testThatClosedLoanApplicationDateRejectedIsSetToCurrentDateTime() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();

        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(new LoanApplication()));
        when(loanRepository.save(any())).thenReturn(any());
        loanApplicationService.closeLoanApplication(String.valueOf(loanApplicationId));
        ArgumentCaptor<LoanApplication> loanCaptor = ArgumentCaptor.forClass(LoanApplication.class);
        verify(loanRepository).save(loanCaptor.capture());
        LoanApplication updateLoanApplication = loanCaptor.getValue();
        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime actualDateTime =    updateLoanApplication.getDateClosed().truncatedTo(ChronoUnit.SECONDS);
        assertEquals(currentDateTime,actualDateTime);
    }
    @Test
    void testViewLoanApplicationStatusInExistingLoanApplicationReturnsLoanApplication() throws ResourceException {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));
   LoanApplication result = loanApplicationService.viewLoanApplicationStatus(String.valueOf(loanApplicationId));
   assertNotNull(result);
   assertSame(loanApplication,result);
   verify(loanRepository,times(1)).findById(String.valueOf(loanApplicationId));
    }
    @Test
    void testViewLoanApplicationStatusInNonExistingLoanApplication(){
        UUID loanApplicationId = UUID.randomUUID();
        when(loanRepository.findById(String.valueOf(loanApplicationId))).thenReturn(Optional.empty());
        assertThrows(ResourceException.class, ()-> loanApplicationService.viewLoanApplicationStatus(String.valueOf(loanApplicationId)));
        verify(loanRepository, times(1)).findById(String.valueOf(loanApplicationId));
    }

}