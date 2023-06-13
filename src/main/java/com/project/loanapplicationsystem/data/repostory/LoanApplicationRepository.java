package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication,String> {


}
