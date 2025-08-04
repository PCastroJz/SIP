package com.beto.sip.domain.auth.exception;

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException(String message) {
        super(message);
    }
}