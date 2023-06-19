package com.project.loanapplicationsystem.data.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private int statusCode;
    private String message;
}
