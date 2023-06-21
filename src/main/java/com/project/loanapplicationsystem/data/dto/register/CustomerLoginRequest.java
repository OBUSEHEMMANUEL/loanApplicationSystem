package com.project.loanapplicationsystem.data.dto.register;

import com.project.loanapplicationsystem.utils.RegexPattern;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerLoginRequest {
    private String emailAddress;

    @Pattern(message = "{Pattern.signupRequest.password}", regexp = RegexPattern.PASSWORD_REGEX)
    private String password;
}
