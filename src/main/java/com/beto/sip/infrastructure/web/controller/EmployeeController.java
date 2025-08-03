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
import com.beto.sip.application.employee.service.EmployeeApplicationService;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponseDto> create(@Validated @RequestBody CreateEmployeeRequest req) {
        return service.createEmployee(restMapper.toCommand(req));
    }

    @PutMapping
    public ApiResponse<EmployeeResponseDto> update(@Validated @RequestBody UpdateEmployeeRequest req) {
        return service.updateEmployee(restMapper.toCommand(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponseDto> get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public ApiResponse<List<EmployeeResponseDto>> list() {
        return service.listEmployees();
    }
}
