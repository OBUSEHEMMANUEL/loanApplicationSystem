package com.project.loanapplicationsystem.data.dto.response;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessResponse {
    private int statusCode;
    private String message;
    private ApplicationStatus applicationStatus;
}
