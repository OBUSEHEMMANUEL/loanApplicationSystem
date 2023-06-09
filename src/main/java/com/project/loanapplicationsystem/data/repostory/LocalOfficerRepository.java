package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.model.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalOfficerRepository extends JpaRepository<LoanOfficer,String> {

}
