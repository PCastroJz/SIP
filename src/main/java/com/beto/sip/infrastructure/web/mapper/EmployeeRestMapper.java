package com.beto.sip.infrastructure.web.mapper;

import com.beto.sip.application.employee.dto.*;
import com.beto.sip.infrastructure.web.dto.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRestMapper {

    public CreateEmployeeCommand toCommand(CreateEmployeeRequest req) {
        return new CreateEmployeeCommand(
                req.getFirstName(),
                req.getLastName(),
                req.getEmployeeNumber(),
                req.getPosition(),
                req.getUserId(),
                req.getCreatedBy());
    }

    public UpdateEmployeeCommand toCommand(UpdateEmployeeRequest req) {
        return new UpdateEmployeeCommand(
                req.getId(),
                req.getFirstName(),
                req.getLastName(),
                req.getEmployeeNumber(),
                req.getPosition(),
                req.getStatus(),
                req.getUserId(),
                req.getUpdatedBy());
    }
}