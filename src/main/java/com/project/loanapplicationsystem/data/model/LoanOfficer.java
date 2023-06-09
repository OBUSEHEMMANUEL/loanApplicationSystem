package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
public class LoanOfficer {
    @Id
    @UuidGenerator
    private int id;
    private String userName;
    private String password;
}
