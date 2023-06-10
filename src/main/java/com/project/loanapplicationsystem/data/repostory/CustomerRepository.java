package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

Optional<Customer> findByEmailAddress(String emailAddress);

}

