package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity

public class LoanStatement {
    @Id
    @UuidGenerator
    private String id;
    private Double loanAmount;
    private String purpose;
    private String repaymentPreferences;
    @ManyToOne
    private Customer customer;
}
