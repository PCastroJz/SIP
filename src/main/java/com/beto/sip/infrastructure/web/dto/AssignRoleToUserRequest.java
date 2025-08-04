package com.beto.sip.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleToUserRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Role ID is required")
    private Long roleId;
}