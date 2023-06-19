package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmTokenRequest{
@NotNull
private String token;
@NotNull
private  String emailAddress;
}
