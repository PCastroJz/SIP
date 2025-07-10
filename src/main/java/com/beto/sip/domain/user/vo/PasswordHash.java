package com.beto.sip.domain.user.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordHash {
    private final String value;

    public static PasswordHash of(String hashed) {
        String v = Objects.requireNonNull(hashed, "PasswordHash cannot be null").trim();
        if (v.isBlank()) throw new IllegalArgumentException("PasswordHash cannot be blank");
        return new PasswordHash(v);
    }
}