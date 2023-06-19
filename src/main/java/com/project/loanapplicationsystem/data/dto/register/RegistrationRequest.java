package com.project.loanapplicationsystem.data.dto.register;

import com.project.loanapplicationsystem.utils.RegexPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
//    @Pattern(message = "{Pattern.signupRequest.password}", regexp = RegexPattern.PASSWORD_REGEX)
    private String password;
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String contactNumber;

}
