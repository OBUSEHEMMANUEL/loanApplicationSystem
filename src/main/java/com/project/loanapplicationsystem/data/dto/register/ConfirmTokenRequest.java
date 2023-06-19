package com.project.loanapplicationsystem.data.dto.register;

import jakarta.validation.constraints.NotNull;

public record ConfirmTokenRequest(@NotNull String token, @NotNull String emailAddress) {
}
