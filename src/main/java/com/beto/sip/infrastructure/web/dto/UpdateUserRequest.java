package com.beto.sip.infrastructure.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    @NotNull
    private Long id;
    private String username;
    private String status;
}