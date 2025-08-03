package com.beto.sip.application.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePermissionCommand {
    private String name;
    private String code;
    private String description;
    private Long createdBy;
    private Long parentPermissionId;
}
