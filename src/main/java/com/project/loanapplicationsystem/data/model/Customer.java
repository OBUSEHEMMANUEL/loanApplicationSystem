package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @UuidGenerator
    private String customerId;
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private String contactNumber;
    private String homeAddress;
    private String dateOfBirth;
    private String bankVerificationNumber;
    private String occupation;
    private double annualIncome;
    private String maritalStatus;
    private String nationality;
    private LocalDateTime dateRegistered;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateCollected;
    private Boolean isEnabled = false;
    @OneToMany(mappedBy="customer" )
    private Set<LoanApplication> loanApplication = new HashSet<>();
    @OneToOne(mappedBy = "customer")
    private ConfirmToken confirmToken;
   }
