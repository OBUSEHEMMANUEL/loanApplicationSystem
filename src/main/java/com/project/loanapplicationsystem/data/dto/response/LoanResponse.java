package com.project.loanapplicationsystem.data.dto.response;

import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
@Data
public class LoanResponse {
    private String customerId;
    private Double loanAmount;
    private String purpose;
    private String repaymentPreferences;
    private LocalDateTime dateApplied;
    private LocalDateTime dateAccepted;
    private LocalDateTime dateRejected;
    private LocalDateTime dateClosed;
    private ApplicationStatus applicationStatus;
}
