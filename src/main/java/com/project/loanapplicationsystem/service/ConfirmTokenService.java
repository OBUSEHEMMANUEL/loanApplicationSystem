package com.project.loanapplicationsystem.service;


import com.project.loanapplicationsystem.data.model.ConfirmToken;

import java.util.Optional;

public interface ConfirmTokenService {
    void saveConfirmationToken(ConfirmToken confirmationToken);
    Optional<ConfirmToken> getConfirmationToken(String token);
    void setConfirmed(String token);
}