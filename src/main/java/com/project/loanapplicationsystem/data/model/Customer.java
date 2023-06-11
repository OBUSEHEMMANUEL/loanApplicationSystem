package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @UuidGenerator
    private String id;
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
    @OneToMany
    private Set<LoanStatement> loanStatement;
   }
