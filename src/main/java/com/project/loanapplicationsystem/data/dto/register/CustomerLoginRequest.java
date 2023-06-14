package com.project.loanapplicationsystem.data.dto.register;

import lombok.Data;

@Data
public class CustomerLoginRequest {
    private String emailAddress;
    private String password;
}
