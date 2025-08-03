package com.beto.sip.application.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleCommand {
    private Long id;
    private String name;
    private String description;
    private String status;
    private Long updatedBy;
}
