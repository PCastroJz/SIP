package com.beto.sip.application.employee.service.impl;

import com.beto.sip.application.employee.dto.*;
import com.beto.sip.application.employee.service.EmployeeApplicationService;
import com.beto.sip.domain.employee.Employee;
import com.beto.sip.domain.employee.exception.EmployeeNotFoundException;
import com.beto.sip.domain.employee.repository.EmployeeRepositoryPort;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeApplicationServiceImpl implements EmployeeApplicationService {

    private final EmployeeRepositoryPort employeeRepo;

    @Override
    public ApiResponse<EmployeeResponseDto> createEmployee(CreateEmployeeCommand cmd) {
        log.info("Creating employee {}", cmd.getEmployeeNumber());

        Instant now = Instant.now();
        Employee employee = Employee.builder()
                .firstName(cmd.getFirstName())
                .lastName(cmd.getLastName())
                .employeeNumber(cmd.getEmployeeNumber())
                .position(cmd.getPosition())
                .userId(cmd.getUserId())
                .status("ACTIVE")
                .createdAt(now)
                .createdBy(cmd.getCreatedBy())
                .updatedAt(now)
                .build();

        Employee saved = employeeRepo.save(employee);
        return ApiResponse.success(toDto(saved), "EMPLOYEE_CREATED", HttpStatus.CREATED);
    }

    @Override
    public ApiResponse<EmployeeResponseDto> updateEmployee(UpdateEmployeeCommand cmd) {
        log.info("Updating employee ID: {}", cmd.getId());

        Employee existing = employeeRepo.findById(cmd.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Employee updated = existing.toBuilder()
                .firstName(cmd.getFirstName() != null ? cmd.getFirstName() : existing.getFirstName())
                .lastName(cmd.getLastName() != null ? cmd.getLastName() : existing.getLastName())
                .employeeNumber(
                        cmd.getEmployeeNumber() != null ? cmd.getEmployeeNumber() : existing.getEmployeeNumber())
                .position(cmd.getPosition() != null ? cmd.getPosition() : existing.getPosition())
                .status(cmd.getStatus() != null ? cmd.getStatus() : existing.getStatus())
                .userId(cmd.getUserId() != null ? cmd.getUserId() : existing.getUserId())
                .updatedAt(Instant.now())
                .updatedBy(cmd.getUpdatedBy())
                .build();

        Employee saved = employeeRepo.save(updated);
        return ApiResponse.success(toDto(saved), "EMPLOYEE_UPDATED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<EmployeeResponseDto> getById(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return ApiResponse.success(toDto(employee), "EMPLOYEE_FOUND", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<EmployeeResponseDto>> listEmployees() {
        List<EmployeeResponseDto> employees = employeeRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (employees.isEmpty()) {
            return ApiResponse.success(employees, "NO_EMPLOYEES_FOUND", HttpStatus.NO_CONTENT);
        }
        return ApiResponse.success(employees, "EMPLOYEES_LISTED", HttpStatus.OK);
    }

    private EmployeeResponseDto toDto(Employee e) {
        return EmployeeResponseDto.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .employeeNumber(e.getEmployeeNumber())
                .position(e.getPosition())
                .status(e.getStatus())
                .userId(e.getUserId())
                .createdAt(e.getCreatedAt())
                .createdBy(e.getCreatedBy())
                .updatedAt(e.getUpdatedAt())
                .updatedBy(e.getUpdatedBy())
                .build();
    }
}