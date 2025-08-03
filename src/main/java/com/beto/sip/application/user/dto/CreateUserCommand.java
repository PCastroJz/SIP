package com.beto.sip.application.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class CreateUserCommand {
    @NotBlank
    private final String username;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
    @NotBlank
    private final Long createdBy;

}