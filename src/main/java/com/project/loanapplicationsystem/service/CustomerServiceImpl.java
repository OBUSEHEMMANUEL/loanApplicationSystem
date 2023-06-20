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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final ConfirmTokenService confirmTokenService;
    private final LoanApplicationService loanApplicationService;
     private final ModelMapper modelMapper;
     private final EmailService emailService;


  private final BCryptPasswordEncoder encoder;

    @Override
    public SuccessResponse register(RegistrationRequest request) throws ResourceException {
   if(customerRepository.findByEmailAddress(request.getEmailAddress()).isPresent()){
        throw new ResourceException("Customer Already Exist");
        }
   Customer customer =   modelMapper.map(request, Customer.class);
   String encodePassword = bcrypt(request.getPassword());
   customer.setPassword(encodePassword);
   customer.setDateRegistered(LocalDateTime.now());
        String token = generateToken(customer);
   customerRepository.save(customer);

        if (token == null) {
            throw new ResourceException("Failed to generate token");
        }
        log.info(token);
     emailService.send(request.getEmailAddress(),buildEmail(request.getLastName(),token));
  return SuccessResponse.builder()
           .message("Customer Registered Successfully")
           .statusCode(HttpStatus.OK.value())
           .build();
    }

    private String generateToken(Customer customer) {
        StringBuilder tok = new StringBuilder();
        SecureRandom number = new SecureRandom();
        for (int i = 0; i < 4; i++) {
            int num = number.nextInt(9);
            tok.append(num);
        }
        StringBuilder token = new StringBuilder(tok.toString());
        ConfirmToken confirmToken =ConfirmToken.builder()
                .token(String.valueOf(token))
                .createdAt(LocalDateTime.now())
                .build();
   confirmTokenService.saveConfirmationToken(confirmToken);
        customer.setConfirmToken(confirmToken);

        return token.toString();
    }

    @Override
    public SuccessResponse updateRegister(UpdateRegistrationRequest request) {
        Customer customerToUpdate = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new RuntimeException("User Not Found"));

      modelMapper.map(request, customerToUpdate);
        customerToUpdate.setDateUpdated(LocalDateTime.now());
        customerRepository.save(customerToUpdate);

        return SuccessResponse.builder()
                .message("Customer Registration Updated Successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
    @Override
    public SuccessResponse login(CustomerLoginRequest request) {
        try {
            Customer foundUser = customerRepository.findByEmailAddress(request.getEmailAddress()).orElseThrow(() -> new RuntimeException("Invalid EmailAddress"));
            if (foundUser.getIsDisabled()) {
                throw new ResourceException("Confirm token to login");
            }
            boolean matches = encoder.matches(request.getPassword(), foundUser.getPassword());

            if (matches) {
                return SuccessResponse.builder()
                        .message("LOGIN SUCCESSFUL")
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .build();
            } else {
                return SuccessResponse.builder()
                        .message("INVALID PASSWORD")
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } catch (ResourceException | RuntimeException e) {
            return SuccessResponse.builder()
                    .message("ERROR: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
    @Override
    public Optional<Customer> findById(String id){
        return customerRepository.findById(id);
}
@Override
public SuccessResponse loanApplication(LoanRequest request) throws ResourceException {
     return    loanApplicationService.loanApplication(request);
}
    @Override
    public ApplicationStatus viewLoanApplicationStatus(String loanApplicationId, String customerId) throws ResourceException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceException("Customer not found"));

        LoanApplication loanApplication = customer.getLoanApplication().stream()
                .filter(app -> app.getId().equals(String.valueOf(loanApplicationId)))
                .findFirst()
                .orElseThrow(() -> new ResourceException("Loan application not found"));

        return loanApplication.getApplicationStatus();
    }

    @Override
    public LoanAgreement getLoanAgreement(String loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanApplicationService.findLoanApplicationById(loanApplicationId).orElseThrow(()->new ResourceException("Loan Application not Found"));

        if (loanApplication.getApplicationStatus() != ApplicationStatus.APPROVED) {
            throw new ResourceException("Loan application is not approved");
        }
        LoanAgreement loanAgreement = loanApplication.getLoanAgreement();
        if (loanAgreement == null) {
            throw new ResourceException("Loan agreement not found for the loan application");
        }
        return loanAgreement;
    }
    @Override
    public String confirmToken(ConfirmTokenRequest confirmToken) throws ResourceException {
        var token = confirmTokenService.getConfirmationToken(confirmToken.getToken())
                .orElseThrow(()-> new ResourceException("Token does not exist"));

        confirmTokenService.setConfirmed(token.getToken());
        enableUser(confirmToken.getEmailAddress());

        return "confirmed";
    }
    @Override
    public String resetPassword(SetPasswordRequest passwordRequest) throws  ResourceException {
       Customer foundCustomer = customerRepository.findByEmailAddress(passwordRequest.getEmailAddress()).orElseThrow(()->new ResourceException("Email not found"));

        foundCustomer.setPassword(encoder.encode(passwordRequest.getNewPassword()));
        customerRepository.save(foundCustomer);
        return "Password reset Successfully";

    }
    @Override
    public String forgottenPassword(ForgottenPasswordRequest request) throws ResourceException {
        Customer foundCustomer =  customerRepository.findByEmailAddress(request.getEmailAddress()).orElseThrow(()-> new ResourceException("Email does not exist"));

        String token = generateToken(foundCustomer);

        ConfirmToken confirmToken = ConfirmToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .build();

        confirmTokenService.saveConfirmationToken(confirmToken);
        foundCustomer.setConfirmToken(confirmToken);
customerRepository.save(foundCustomer);
        emailService.send(request.getEmailAddress(), buildEmail(foundCustomer.getLastName(),token));

        return  "Token sent to your email Successfully";
    }
    private void enableUser(String emailAddress) {
        customerRepository.enable(emailAddress);
    }


    private String bcrypt(String password){
        return encoder.encode(password);
    }
    public String buildEmail(String name, String token) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\enum-v2\\loanApplicationSystem\\src\\main\\resources\\templates\\email_template.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("{name}")) {
                    line = line.replace("{name}", name);
                }
                if (line.contains("{token}")) {
                    line = line.replace("{token}", token);
                }
                content.append(line).append("\n");
            }
            reader.close();

        } catch (IOException e) {

            SuccessResponse.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
        return content.toString();
    }
}
