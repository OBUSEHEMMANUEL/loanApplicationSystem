package com.project.loanapplicationsystem.data.repostory;

import com.project.loanapplicationsystem.data.dto.register.RegistrationRequest;
import com.project.loanapplicationsystem.data.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

Optional<Customer> findByEmailAddress(String emailAddress);

    @Transactional
    @Modifying
    @Query("UPDATE  Customer  customer " +
            "SET customer.isDisabled = false" +
            " WHERE customer.emailAddress=?1")
    void enable(String emailAddress);

}

