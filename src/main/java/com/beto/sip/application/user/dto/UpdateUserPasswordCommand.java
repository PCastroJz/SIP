package com.beto.sip.application.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserPasswordCommand {
    @NotNull
    private final Long id;
    @NotNull
    private final String password;
    @NotNull
    private final Long updatedBy;
}
