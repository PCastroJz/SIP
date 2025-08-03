package com.beto.sip.application.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionResponseDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String status;
}
