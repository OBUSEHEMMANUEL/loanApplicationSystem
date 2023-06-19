package com.project.loanapplicationsystem.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmToken {
    @Id
    @UuidGenerator
    private String id;

    private String token;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

   @OneToOne
    private Customer customer;
}
