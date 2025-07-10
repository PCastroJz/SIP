package com.beto.sip.domain.user.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Username {
    private final String value;

    public static Username of(String value) {
        String trimmed = Objects.requireNonNull(value, "Username cannot be null").trim();
        if (trimmed.isBlank()) throw new IllegalArgumentException("Username cannot be blank");
        return new Username(trimmed);
    }
}