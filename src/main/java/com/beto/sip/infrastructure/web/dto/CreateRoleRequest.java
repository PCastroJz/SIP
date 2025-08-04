package com.beto.sip.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleRequest {
    @NotBlank(message = "Role name is required")
    private String name;

    private String description;
}