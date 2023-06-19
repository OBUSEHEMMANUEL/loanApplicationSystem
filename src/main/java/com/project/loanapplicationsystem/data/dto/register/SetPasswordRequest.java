package com.project.loanapplicationsystem.data.dto.register;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String newPassword;
    private String emailAddress;
}
