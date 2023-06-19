package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanAgreement {
    @Id
    @UuidGenerator
    private String id;
    private String loanTerms;
    private String repaymentSchedule;
    private LocalDate agreementDate;
    private BigDecimal interestRate;
    @OneToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

}
