package com.project.loanapplicationsystem.data.dto.register;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailAddress;
    private String password;
}
