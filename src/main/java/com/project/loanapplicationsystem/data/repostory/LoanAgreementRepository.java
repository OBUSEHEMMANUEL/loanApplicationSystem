package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.model.LoanAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanAgreementRepository  extends JpaRepository<LoanAgreement, String> {
}
