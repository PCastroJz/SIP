package com.beto.sip.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String code, HttpStatus status) {
        return ApiResponse.<T>builder()
                .code(code)
                .httpStatus(status)
                .message("Operation successful")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String code, HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .httpStatus(status)
                .message(message)
                .data(null)
                .build();
    }
}
