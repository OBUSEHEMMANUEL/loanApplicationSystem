package com.project.loanapplicationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LoanApplicationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplicationSystemApplication.class, args);
    }
}
