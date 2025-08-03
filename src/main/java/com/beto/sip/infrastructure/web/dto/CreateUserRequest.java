package com.beto.sip.infrastructure.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank private String username;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    @NotNull private Long createdBy;
}