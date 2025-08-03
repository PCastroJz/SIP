package com.beto.sip.application.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignPermissionToRoleCommand {
    private Long roleId;
    private Long permissionId;
    private Long createdBy;
}
