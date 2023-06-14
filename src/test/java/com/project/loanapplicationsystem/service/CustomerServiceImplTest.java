package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.CustomerLoginRequest;
import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.register.UpdateRegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerServiceImpl.class)
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;
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
    void testThatCustomerCanRegister() {
        Customer mappedCustomer = new ModelMapper().map(registrationRequest, Customer.class);
        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.empty());
        when(modelMapper.map(registrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(encoder.encode(registrationRequest.getPassword())).thenReturn("EncodedPassword");
        when(customerRepository.save(any())).thenReturn(any());
         SucessResponse customerResponse = customerService.register(registrationRequest);
        verify(customerRepository).save(any());
        assertNotNull(customerResponse);
       assertEquals(HttpStatus.OK.value(),customerResponse.getStatusCode());
       verify(customerRepository,timeout(1)).save(any(Customer.class));
    }

    @Test
    void testThatExistingCustomerCannotRegisterTwiceWithSameMail(){
        String existingEmailAddress = "Emmanuel@gmail.com";
        when(customerRepository.findByEmailAddress(existingEmailAddress)).thenReturn(Optional.of(new Customer()));
        assertThrows(RuntimeException.class,()->customerService.register(registrationRequest));
    }

    @Test
    void testThatUpdateRegisterDateRegisteredIsSetToCurrentDateTime(){
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
        SucessResponse customerResponse = customerService.updateRegister(updateRegistrationRequest);
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

  SucessResponse customerLogin = customerService.login(customerLoginRequest);

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

        SucessResponse customerLogin = customerService.login(customerLoginRequest);

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

        SucessResponse customerLogin = customerService.login(customerLoginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),customerLogin.getStatusCode());
        assertEquals("ERROR: Invalid EmailAddress",customerLogin.getMessage());

    }



}