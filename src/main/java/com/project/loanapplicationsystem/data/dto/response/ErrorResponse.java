package com.project.loanapplicationsystem.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class ErrorResponse {
    @NonNull
    private Object message;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
}
