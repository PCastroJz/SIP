package com.beto.sip.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.beto.sip.domain.employee.Employee;
import com.beto.sip.infrastructure.persistence.entity.EmployeeEntity;

@Component
public class EmployeePersistenceMapper {
    public Employee toDomain(EmployeeEntity e) {
        return Employee.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .employeeNumber(e.getEmployeeNumber())
                .position(e.getPosition())
                .userId(e.getUserId())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .createdBy(e.getCreatedBy())
                .updatedAt(e.getUpdatedAt())
                .updatedBy(e.getUpdatedBy())
                .build();
    }

    public EmployeeEntity toEntity(Employee e) {
        return EmployeeEntity.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .employeeNumber(e.getEmployeeNumber())
                .position(e.getPosition())
                .userId(e.getUserId())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .createdBy(e.getCreatedBy())
                .updatedAt(e.getUpdatedAt())
                .updatedBy(e.getUpdatedBy())
                .build();
    }
}
