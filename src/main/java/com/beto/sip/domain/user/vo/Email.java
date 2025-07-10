package com.beto.sip.domain.user.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Email {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
    private final String value;

    public static Email of(String value) {
        Objects.requireNonNull(value, "Email cannot be null");
        String trimmed = value.trim().toLowerCase();
        if (!EMAIL_REGEX.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new Email(trimmed);
    }
}