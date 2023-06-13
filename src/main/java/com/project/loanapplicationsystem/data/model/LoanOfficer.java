package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data
@Entity
public class LoanOfficer {
    @Id
    @UuidGenerator
    private String id;
    private String userName;
    private String password;
    @OneToMany
    private List<Customer> customerList;
}
