package com.beto.sip.domain.user.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Objects;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserId {
    private final Long value;

    public static UserId of(Long value) {
        Objects.requireNonNull(value, "UserId cannot be null");
        return new UserId(value);
    }
}