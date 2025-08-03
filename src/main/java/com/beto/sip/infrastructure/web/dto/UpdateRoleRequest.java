package com.beto.sip.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
    @NotNull(message = "Role ID is required")
    private Long id;

    private String name;
    private String description;
    private String status;
    private Long updatedBy;
}