package com.beto.sip.application.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class UpdateUserCommand {
    @NotNull
    private final Long id;
    @NotBlank
    private final String username;
    @Email
    @NotBlank
    private final String email;
    private final String status;
}