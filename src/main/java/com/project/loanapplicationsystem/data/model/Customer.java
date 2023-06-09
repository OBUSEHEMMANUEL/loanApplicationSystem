package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @UuidGenerator
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;
    private String contactNumber;
    private String homeAddress;
    private LocalDate dateOfBirth;
    private String bankVerificationNumber;
    private String occupation;
    private double annualIncome;
    private String maritalStatus;
    private String nationality;
    private LocalDateTime dateRegistered;
    private LocalDateTime dateCollected;
   }
