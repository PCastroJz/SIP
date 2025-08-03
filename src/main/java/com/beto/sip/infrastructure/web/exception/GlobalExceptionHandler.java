package com.beto.sip.infrastructure.web.exception;

import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.domain.auth.exception.PermissionAlreadyExistsException;
import com.beto.sip.domain.auth.exception.PermissionNotFoundException;
import com.beto.sip.domain.auth.exception.RoleAlreadyExistsException;
import com.beto.sip.domain.auth.exception.RoleNotFoundException;
import com.beto.sip.domain.employee.Employee;
import com.beto.sip.domain.employee.exception.EmployeeNotFoundException;
import com.beto.sip.domain.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<?> handleUserNotFound(UserNotFoundException ex) {
        return ApiResponse.error("USER_NOT_FOUND", HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ApiResponse<?> handleUserExists(UserAlreadyExistsException ex) {
        return ApiResponse.error("USER_EXISTS", HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleBadRequest(IllegalArgumentException ex) {
        return ApiResponse.error("BAD_REQUEST", HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleGeneric(Exception ex) {
        return ApiResponse.error("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ApiResponse<?> handleRoleNotFound(RoleNotFoundException ex) {
        return ApiResponse.error("ROLE_NOT_FOUND", HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ApiResponse<?> handleRoleAlreadyExists(RoleAlreadyExistsException ex) {
        return ApiResponse.error("ROLE_EXISTS", HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(PermissionNotFoundException.class)
    public ApiResponse<?> handlePermissionNotFound(PermissionNotFoundException ex) {
        return ApiResponse.error("PERMISSION_NOT_FOUND", HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PermissionAlreadyExistsException.class)
    public ApiResponse<?> handlePermissionAlreadyExists(PermissionAlreadyExistsException ex) {
        return ApiResponse.error("PERMISSION_EXISTS", HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ApiResponse<?> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ApiResponse.error("EMPLOYEE_NOT_FOUND", HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
