package com.project.loanapplicationsystem.exception;

import com.project.loanapplicationsystem.data.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> globalExceptionHandler(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ErrorResponse(fieldErrors));
    }
}
