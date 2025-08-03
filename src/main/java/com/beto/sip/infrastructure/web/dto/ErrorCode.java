package com.beto.sip.infrastructure.web.dto;

public enum ErrorCode {
    USER_EXISTS("USER_EXISTS"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    NO_USERS_FOUND("NO_USERS_FOUND"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
