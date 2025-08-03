package com.beto.sip.application.employee.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class EmployeeResponseDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String employeeNumber;
    private final String position;
    private final String status;
    private final Long userId;
    private final Instant createdAt;
    private final Long createdBy;
    private final Instant updatedAt;
    private final Long updatedBy;
}