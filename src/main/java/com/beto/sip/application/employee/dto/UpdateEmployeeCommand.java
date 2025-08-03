package com.beto.sip.application.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEmployeeCommand {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String employeeNumber;
    private final String position;
    private final String status;
    private final Long userId;
    private final Long updatedBy;
}