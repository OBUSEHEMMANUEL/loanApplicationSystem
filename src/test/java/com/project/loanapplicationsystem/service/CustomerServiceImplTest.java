package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.repostory.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @MockBean
    private  CustomerRepository customerRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ModelMapper modelMapper;


    @Autowired
    private CustomerService customerService;

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationRequest  = RegistrationRequest.builder()
                .firstName("Emmanuel")
                .lastName("obuseh")
                .password("Emmanuel123")
                .annualIncome(5000)
                .bankVerificationNumber("1234567")
                .contactNumber("+234706905")
                .emailAddress("Emmanuel@gmail.com")
                .homeAddress("Sabo yaba lagos")
                .maritalStatus("SINGLE")
                .nationality("Nigeria")
                .occupation("Software Engineer")
                .dateOfBirth("2021-05-22")
                .build();
    }
//
//    @Test
//    void register() {
//        when(customerRepository.findById(talent.getId())).thenReturn(Optional.ofNullable(talent));
//        userService.updateBioData(user, bioDataRequest);
//        ArgumentCaptor<EnumUser> talentArgumentCaptor = ArgumentCaptor.forClass(EnumUser.class);
//    }
}