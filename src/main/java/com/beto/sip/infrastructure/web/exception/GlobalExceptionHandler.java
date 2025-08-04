package com.beto.sip.infrastructure.web.exception;

import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.domain.auth.exception.CustomAccessDeniedException;
import com.beto.sip.domain.auth.exception.InvalidCredentialsException;
import com.beto.sip.domain.auth.exception.PermissionAlreadyExistsException;
import com.beto.sip.domain.auth.exception.PermissionNotFoundException;
import com.beto.sip.domain.auth.exception.RoleAlreadyExistsException;
import com.beto.sip.domain.auth.exception.RoleNotFoundException;
import com.beto.sip.domain.employee.exception.EmployeeNotFoundException;
import com.beto.sip.domain.user.exception.*;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ApiResponse<Void> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ApiResponse.error(
                "INVALID_CREDENTIALS",
                HttpStatus.UNAUTHORIZED,
                ex.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ApiResponse<Void> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        return ApiResponse.error(
                "ACCESS_DENIED",
                HttpStatus.FORBIDDEN,
                "No tienes permisos para acceder a este recurso");
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ApiResponse<Void> handleCustomAccessDenied(CustomAccessDeniedException ex) {
        return ApiResponse.error(
                "ACCESS_DENIED",
                HttpStatus.FORBIDDEN,
                ex.getMessage() != null ? ex.getMessage() : "No tienes permisos para acceder a este recurso");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthenticationException(AuthenticationException ex) {
        return ApiResponse.error(
                "UNAUTHORIZED",
                HttpStatus.UNAUTHORIZED,
                "No estás autenticado o tu token no es válido");
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleGenericException(Exception ex) {
        return ApiResponse.error(
                "INTERNAL_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error occurred");
    }

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
