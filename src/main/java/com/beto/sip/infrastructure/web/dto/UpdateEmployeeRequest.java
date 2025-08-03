package com.beto.sip.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeRequest {
    @NotNull(message = "Employee ID is required")
    private Long id;

    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String position;
    private String status;
    private Long userId;
    private Long updatedBy;
}