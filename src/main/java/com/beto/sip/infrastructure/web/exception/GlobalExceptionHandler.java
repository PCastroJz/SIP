package com.beto.sip.infrastructure.web.exception;

import com.beto.sip.infrastructure.web.dto.ApiResponse;
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
}
