package com.project.loanapplicationsystem.utils;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ApiResponse {
    private ZonedDateTime timeStamp;
    private int statusCode;
    private  Object data;
    private Boolean isSuccessful;
}