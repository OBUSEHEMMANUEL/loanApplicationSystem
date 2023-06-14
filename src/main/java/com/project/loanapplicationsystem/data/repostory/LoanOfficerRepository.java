package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer,String> {
    Optional<LoanOfficer> findByUserName(String emailAddress);
}
