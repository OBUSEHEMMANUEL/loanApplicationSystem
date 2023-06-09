package com.project.loanapplicationsystem.data.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SucessResponse {
    private int StatusCode;
    private String message;
}
