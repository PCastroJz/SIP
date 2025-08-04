package com.beto.sip.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotNull
    private Long id;
    @NotNull
    private String password;
}
