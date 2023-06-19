package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanOfficerLoginRequest;
import com.project.loanapplicationsystem.data.dto.response.SuccessResponse;
import com.project.loanapplicationsystem.data.model.LoanOfficer;
import com.project.loanapplicationsystem.data.repostory.LoanOfficerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LoanOfficerServiceImpl.class)
class LoanOfficerServiceImplTest {
@MockBean
    private  LoanOfficerRepository loanOfficerRepository;
@MockBean
    private LoanApplicationService loanApplicationService;
@MockBean
    private CustomerService customerService;
@MockBean
private LoanAgreementService loanAgreementService;
@MockBean
    private  BCryptPasswordEncoder encoder;
@Autowired
private LoanOfficerService officerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }


    @Test
    void testThatLoanOfficerCanLoginAndSuccessResponseLoginSuccessfulIsReturned(){
        String username = "Admin";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoanOfficer loanOfficer = new LoanOfficer();
        loanOfficer.setUserName(username);
        loanOfficer.setPassword(encodedPassword);

        LoanOfficerLoginRequest loginRequest = new LoanOfficerLoginRequest();
        loginRequest.setUserName(username);
        loginRequest.setPassword(password);


        when(loanOfficerRepository.findByUserNameIgnoreCase(any())).thenReturn(Optional.of(loanOfficer));
        when(encoder.matches(password,encodedPassword)).thenReturn(true);

        SuccessResponse officerLogin = officerService.loanOfficerLogin(loginRequest);

        assertEquals(HttpStatus.ACCEPTED.value(),officerLogin.getStatusCode());
        assertEquals("LOGIN SUCCESSFUL",officerLogin.getMessage());

    }

    @Test
    void testThatLoanOfficerUsesInvalidToPasswordLoginAndShouldReturnInvalidPassword(){
        String username = "Admin";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoanOfficer loanOfficer = new LoanOfficer();
        loanOfficer.setUserName(username);
        loanOfficer.setPassword(encodedPassword);

        LoanOfficerLoginRequest loginRequest = new LoanOfficerLoginRequest();
        loginRequest.setUserName(username);
        loginRequest.setPassword(password);

        when(loanOfficerRepository.findByUserNameIgnoreCase(any())).thenReturn(Optional.of(loanOfficer));
        when(encoder.matches(password,encodedPassword)).thenReturn(false);

        SuccessResponse officerLogin = officerService.loanOfficerLogin(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED.value(),officerLogin.getStatusCode());
        assertEquals("INVALID PASSWORD",officerLogin.getMessage());

    }

    @Test
    void testThatCustomerLoginWithInvalidEmailAddressAndShouldReturnInternalServerErrorResponse(){
        String username = "Admin";
        String password = "password";
        String encodedPassword = "encodedPassword";

        LoanOfficer loanOfficer = new LoanOfficer();
        loanOfficer.setUserName(username);
        loanOfficer.setPassword(encodedPassword);

        LoanOfficerLoginRequest loginRequest = new LoanOfficerLoginRequest();
        loginRequest.setUserName(username);
        loginRequest.setPassword(password);

        when(loanOfficerRepository.findByUserNameIgnoreCase(any())).thenReturn(Optional.empty());
        when(encoder.matches(password,encodedPassword)).thenReturn(true);

        SuccessResponse officerLogin = officerService.loanOfficerLogin(loginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),officerLogin.getStatusCode());
        assertEquals("ERROR: Officer not Registered",officerLogin.getMessage());
    }

}