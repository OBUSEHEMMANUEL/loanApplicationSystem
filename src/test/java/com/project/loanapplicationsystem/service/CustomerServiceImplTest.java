package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.*;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.ConfirmToken;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanAgreement;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import com.project.loanapplicationsystem.service.emailService.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerServiceImpl.class)
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;
@MockBean
    private LoanApplicationService loanApplicationService;
@MockBean
    private  ConfirmTokenService confirmTokenService;
@MockBean
    private EmailService emailService;
    @MockBean
    private ModelMapper modelMapper;
@MockBean
    private BCryptPasswordEncoder encoder;


    @Autowired
    private CustomerService customerService;


    private RegistrationRequest registrationRequest;
    private UpdateRegistrationRequest updateRegistrationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationRequest = RegistrationRequest.builder()
                .firstName("Emmanuel")
                .lastName("obuseh")
                .password("Emmanuel123")
                .contactNumber("+234706905")
                .emailAddress("Emmanuel@gmail.com")
                .build();

        updateRegistrationRequest = UpdateRegistrationRequest.builder()
                .customerId("afhjhkjg")
                .annualIncome(5000)
                .bankVerificationNumber("1234567")
                .homeAddress("Sabo yaba lagos")
                .maritalStatus("SINGLE")
                .nationality("Nigeria")
                .occupation("Software Engineer")
                .dateOfBirth("2021-05-22")
                .build();
    }

    @Test
    void testThatCustomerCanRegister() throws ResourceException {
        Customer mappedCustomer = new ModelMapper().map(registrationRequest, Customer.class);
        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.empty());
        when(modelMapper.map(registrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(encoder.encode(registrationRequest.getPassword())).thenReturn("EncodedPassword");
        when(customerRepository.save(any())).thenReturn(any());

         SuccessResponse customerResponse = customerService.register(registrationRequest);
        verify(emailService).send(any(),any());
        verify(customerRepository).save(any());
        assertNotNull(customerResponse);
       assertEquals(HttpStatus.OK.value(),customerResponse.getStatusCode());
       verify(customerRepository,timeout(1)).save(any(Customer.class));
    }

    @Test
    void testThatExistingCustomerCannotRegisterTwiceWithSameMail(){
        String existingEmailAddress = "Emmanuel@gmail.com";
        when(customerRepository.findByEmailAddress(existingEmailAddress)).thenReturn(Optional.of(new Customer()));
        assertThrows(ResourceException.class,()->customerService.register(registrationRequest));
    }

    @Test
    void testThatUpdateRegisterDateRegisteredIsSetToCurrentDateTime() throws ResourceException {
        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.empty());
        Customer mappedCustomer = new ModelMapper().map(registrationRequest, Customer.class);
        when(modelMapper.map(registrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(customerRepository.save(any())).thenReturn(any());
        customerService.register(registrationRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());

        Customer updateCustomer = customerCaptor.getValue();
        LocalDateTime  currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime actualDateTime =    updateCustomer.getDateRegistered().truncatedTo(ChronoUnit.SECONDS);
        assertEquals(currentDateTime,actualDateTime);
    }

    @Test
    void testThatCustomerCanUpdateRegistration() {
       Customer editCustomer = Customer.builder()
                .annualIncome(5000)
                .bankVerificationNumber("1234567")
                .homeAddress("Sabo yaba lagos")
                .maritalStatus("SINGLE")
                .nationality("Nigeria")
                .occupation("Software Engineer")
                .dateOfBirth("2021-05-22")
                .build();

        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(editCustomer));
        Customer mappedCustomer = new ModelMapper().map(updateRegistrationRequest, Customer.class);
        when(modelMapper.map(updateRegistrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(customerRepository.save(any())).thenReturn(any());
        SuccessResponse customerResponse = customerService.updateRegister(updateRegistrationRequest);
        verify(customerRepository).save(any());
        assertNotNull(customerResponse);
        assertEquals(HttpStatus.OK.value(),customerResponse.getStatusCode());
        verify(customerRepository,timeout(1)).save(any(Customer.class));
    }

    @Test
    void testThatWhenCustomerIsNotFoundItThrowsException(){
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()-> customerService.updateRegister(updateRegistrationRequest));
    }
    @Test
    void testThatUpdateRegisterDateUpdatedIsSetToCurrentDateTime(){
        Customer editCustomer = Customer.builder()
                .annualIncome(5000)
                .bankVerificationNumber("1234567")
                .homeAddress("Sabo yaba lagos")
                .maritalStatus("SINGLE")
                .nationality("Nigeria")
                .occupation("Software Engineer")
                .dateOfBirth("2021-05-22")
                .build();
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(editCustomer));
        Customer mappedCustomer = new ModelMapper().map(updateRegistrationRequest, Customer.class);
        when(modelMapper.map(updateRegistrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(customerRepository.save(any())).thenReturn(any());
        customerService.updateRegister(updateRegistrationRequest);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());

       Customer updateCustomer = customerCaptor.getValue();
     LocalDateTime  currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    LocalDateTime actualDateTime =    updateCustomer.getDateUpdated().truncatedTo(ChronoUnit.SECONDS);
     assertEquals(currentDateTime,actualDateTime);
    }

@Test
    void testThatCustomerCanLoginAndSuccessResponseLoginSuccessfulIsReturned(){
    String emailAddress = "Emmanuel@gmail.com";
    String password ="password";
    String encodedPassword = "encodedPassword";

    Customer customer = new Customer();
    customer.setPassword(encodedPassword);
    customer.setEmailAddress("Emmanuel@gmail.com");


    CustomerLoginRequest customerLoginRequest =  new CustomerLoginRequest();
    customerLoginRequest.setPassword(emailAddress);
    customerLoginRequest.setPassword(password);

    when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.of(customer));
    when(encoder.matches(password,encodedPassword)).thenReturn(true);

  SuccessResponse customerLogin = customerService.login(customerLoginRequest);

  assertEquals(HttpStatus.ACCEPTED.value(),customerLogin.getStatusCode());
  assertEquals("LOGIN SUCCESSFUL",customerLogin.getMessage());

}

    @Test
    void testThatCustomerUsesInvalidPasswordLoginAndShouldReturnInvalidPassword(){
        String emailAddress = "Emmanuel@gmail.com";
        String password ="password";
        String encodedPassword = "encodedPassword";

        Customer customer = new Customer();
        customer.setPassword(encodedPassword);
        customer.setEmailAddress("Emmanuel@gmail.com");


        CustomerLoginRequest customerLoginRequest =  new CustomerLoginRequest();
        customerLoginRequest.setPassword(emailAddress);
        customerLoginRequest.setPassword(password);

        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.of(customer));
        when(encoder.matches(password,encodedPassword)).thenReturn(false);

        SuccessResponse customerLogin = customerService.login(customerLoginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED.value(),customerLogin.getStatusCode());
        assertEquals("INVALID PASSWORD",customerLogin.getMessage());

    }

    @Test
    void testThatCustomerLoginWithInvalidEmailAddressAndShouldReturnInternalServerErrorResponse(){
        String emailAddress = "Emmanuel@gmail.com";
        String password ="password";
        String encodedPassword = "encodedPassword";

        Customer customer = new Customer();
        customer.setPassword(encodedPassword);
        customer.setEmailAddress("wrong@gmail.com");


        CustomerLoginRequest customerLoginRequest =  new CustomerLoginRequest();
        customerLoginRequest.setPassword(emailAddress);
        customerLoginRequest.setPassword(password);

        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.empty());
        when(encoder.matches(password,encodedPassword)).thenReturn(true);

        SuccessResponse customerLogin = customerService.login(customerLoginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),customerLogin.getStatusCode());
        assertEquals("ERROR: Invalid EmailAddress",customerLogin.getMessage());

    }
    @Test
    void testToViewLoanApplicationStatus() throws ResourceException {

        UUID loanApplicationId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setCustomerId(String.valueOf(customerId));
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(String.valueOf(loanApplicationId));
        loanApplication.setApplicationStatus(ApplicationStatus.APPROVED);
        customer.getLoanApplication().add(loanApplication);

        when(customerService.findById(String.valueOf(customerId))).thenReturn(Optional.of(customer));


        ApplicationStatus result = customerService.viewLoanApplicationStatus(loanApplicationId, customerId);


        assertEquals(ApplicationStatus.APPROVED, result);
        verify(customerRepository, times(1)).findById(String.valueOf(customerId));
    }

    @Test
    void testToGetCustomerLoanAgreement() throws Exception {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.APPROVED);

        LoanAgreement loanAgreement = new LoanAgreement();
        loanApplication.setLoanAgreement(loanAgreement);
        when(loanApplicationService.findLoanApplicationById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        LoanAgreement result = customerService.getLoanAgreement(loanApplicationId);
        assertSame(loanAgreement, result);

        verify(loanApplicationService, times(1)).findLoanApplicationById(String.valueOf(loanApplicationId));
    }
@Test
    void testToGetLoanAgreementLoanApplicationNotApproved() throws Exception {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
        when(loanApplicationService.findLoanApplicationById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        assertThrows(ResourceException.class, () -> customerService.getLoanAgreement(loanApplicationId));
        verify(loanApplicationService, times(1)).findLoanApplicationById(String.valueOf(loanApplicationId));
    }

    @Test
    void testToGetLoanAgreementLoanAgreementNotFound() throws Exception {
        UUID loanApplicationId = UUID.randomUUID();
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicationStatus(ApplicationStatus.APPROVED);

        // Mock the loan application service to return the loan application without a loan agreement
        when(loanApplicationService.findLoanApplicationById(String.valueOf(loanApplicationId))).thenReturn(Optional.of(loanApplication));

        // Perform the method invocation and expect a ResourceException to be thrown
        assertThrows(ResourceException.class, () -> customerService.getLoanAgreement(loanApplicationId));

        // Verify that the loan application service was called with the correct loan application ID
        verify(loanApplicationService, times(1)).findLoanApplicationById(String.valueOf(loanApplicationId));
    }


    @Test
    void testThatConfirmTokenValidTokenAndEmailAddressReturnConfirmed() throws ResourceException {

        String validToken = "valid_token";
        String emailAddress = "derek@gmail.com";
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest(validToken, emailAddress);
        ConfirmToken token = ConfirmToken.builder()
                .token(validToken)
                .confirmedAt(LocalDateTime.now())
                        .build();

        when(confirmTokenService.getConfirmationToken(validToken)).thenReturn(Optional.of(token));
        doNothing().when(confirmTokenService).setConfirmed(validToken);

        String result = customerService.confirmToken(confirmTokenRequest);

        // Assert
        assertEquals("confirmed", result);
        verify(confirmTokenService, times(1)).getConfirmationToken(validToken);
        verify(confirmTokenService, times(1)).setConfirmed(validToken);
    }

    @Test
    void testThatConfirmTokenWhenInvalidThrowResourceException() throws ResourceException {


        String invalidToken = "invalid_token";
        String emailAddress = "derek@gmail.com";
        ConfirmTokenRequest confirmTokenRequest = new ConfirmTokenRequest(invalidToken, emailAddress);
        when(confirmTokenService.getConfirmationToken(invalidToken)).thenReturn(Optional.empty());


        assertThrows(ResourceException.class, () -> customerService.confirmToken(confirmTokenRequest));
        verify(confirmTokenService, times(1)).getConfirmationToken(invalidToken);
        verify(confirmTokenService, never()).setConfirmed(any());

    }
    @Test
    void testResetPassword() throws ResourceException {
        // Arrange
        String emailAddress = "derek@gmail.com";
        String newPassword = "newpassword";
        Customer customer = new Customer();
        customer.setEmailAddress(emailAddress);
        customer.setPassword("oldpassword"); // Set initial password
        when(customerRepository.findByEmailAddress(emailAddress)).thenReturn(Optional.of(customer));

        SetPasswordRequest passwordRequest = new SetPasswordRequest();
        passwordRequest.setEmailAddress(emailAddress);
        passwordRequest.setNewPassword(newPassword);

        // Act
        String result = customerService.resetPassword(passwordRequest);

        // Assert
        assertEquals("Password reset Successfully", result);
        assertEquals(newPassword, customer.getPassword()); // Verify that the password is updated
        verify(customerRepository, times(1)).findByEmailAddress(emailAddress);
    }
}

