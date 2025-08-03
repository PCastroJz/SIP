package com.beto.sip.application.user.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Getter
@Builder
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String email;
    private final String status;
    private final Instant createdAt;
    private final Long createdBy;
    private final Instant updatedAt;
    private final Long updatedBy;
}