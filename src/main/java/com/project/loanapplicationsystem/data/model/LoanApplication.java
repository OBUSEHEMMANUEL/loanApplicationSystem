package com.project.loanapplicationsystem.data.model;

import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
public class LoanApplication {
    @Id
    @UuidGenerator
    private String id;
    private Double loanAmount;
    private String purpose;
    private String repaymentPreferences;
    private LocalDateTime dateApplied;
    private LocalDateTime dateAccepted;
    private LocalDateTime dateRejected;
    private LocalDateTime dateClosed;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    @ManyToOne
    private Customer customer;
}
