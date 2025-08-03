package com.beto.sip.application.employee.service;

import com.beto.sip.application.employee.dto.*;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import java.util.List;

public interface EmployeeApplicationService {
    ApiResponse<EmployeeResponseDto> createEmployee(CreateEmployeeCommand cmd);

    ApiResponse<EmployeeResponseDto> updateEmployee(UpdateEmployeeCommand cmd);

    ApiResponse<EmployeeResponseDto> getById(Long id);

    ApiResponse<List<EmployeeResponseDto>> listEmployees();
}