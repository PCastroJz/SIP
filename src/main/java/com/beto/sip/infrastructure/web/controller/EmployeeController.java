package com.beto.sip.infrastructure.web.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.beto.sip.application.employee.dto.EmployeeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.beto.sip.application.employee.service.EmployeeApplicationService;
import com.beto.sip.infrastructure.security.CustomUserPrincipal;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.CreateEmployeeRequest;
import com.beto.sip.infrastructure.web.dto.UpdateEmployeeRequest;
import com.beto.sip.infrastructure.web.mapper.EmployeeRestMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeApplicationService service;
    private final EmployeeRestMapper restMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponseDto> create(@Validated @RequestBody CreateEmployeeRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long createdBy = principal.getId();
        return service.createEmployee(restMapper.toCommand(req, createdBy));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<EmployeeResponseDto> update(@Validated @RequestBody UpdateEmployeeRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long updatedBy = principal.getId();
        return service.updateEmployee(restMapper.toCommand(req, updatedBy));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW') or hasRole('ADMIN')")
    public ApiResponse<EmployeeResponseDto> get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW') or hasRole('ADMIN')")
    public ApiResponse<List<EmployeeResponseDto>> list() {
        return service.listEmployees();
    }
}
