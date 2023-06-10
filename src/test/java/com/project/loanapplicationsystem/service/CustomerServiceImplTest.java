package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerServiceImpl.class)
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;
 ;
    @MockBean
    private ModelMapper modelMapper;


  @Autowired
    private CustomerService customerService;

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationRequest = RegistrationRequest.builder()
                .firstName("Emmanuel")
                .lastName("obuseh")
                .password("Emmanuel123")
                .annualIncome(5000)
                .bankVerificationNumber("1234567")
                .contactNumber("+234706905")
                .emailAddress("obuseh1@gmail.com")
                .homeAddress("Sabo yaba lagos")
                .maritalStatus("SINGLE")
                .nationality("Nigeria")
                .occupation("Software Engineer")
                .dateOfBirth("2021-05-22")
                .build();
    }

    @Test
    void testThatCustomerRegister() {
        Customer mappedCustomer = new ModelMapper().map(registrationRequest, Customer.class);
        when(customerRepository.findByEmailAddress(any())).thenReturn(Optional.empty());
        when(modelMapper.map(registrationRequest, Customer.class)).thenReturn(mappedCustomer);
        when(customerRepository.save(any())).thenReturn(any());
         SucessResponse customerResponse = customerService.register(registrationRequest);
        verify(customerRepository).save(any());
        assertNotNull(customerResponse);
       assertEquals(HttpStatus.OK.value(),customerResponse.getStatusCode());
       verify(customerRepository,timeout(1)).save(any(Customer.class));
    }




}