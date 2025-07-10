package com.beto.sip.domain.shared;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditableEntity {
    private final Instant createdAt;
    private final Instant updatedAt;
}