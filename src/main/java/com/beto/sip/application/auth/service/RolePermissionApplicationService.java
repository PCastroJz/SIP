package com.beto.sip.application.auth.service;

import com.beto.sip.application.auth.dto.CreateRoleCommand;
import com.beto.sip.application.auth.dto.UnassignRoleFromUserCommand;
import com.beto.sip.application.auth.dto.CreatePermissionCommand;
import com.beto.sip.application.auth.dto.AssignRoleToUserCommand;
import com.beto.sip.application.auth.dto.AssignPermissionToRoleCommand;
import com.beto.sip.application.auth.dto.RoleResponseDto;
import com.beto.sip.application.auth.dto.UnassignPermissionFromRoleCommand;
import com.beto.sip.application.auth.dto.UpdatePermissionCommand;
import com.beto.sip.application.auth.dto.UpdateRoleCommand;
import com.beto.sip.application.auth.dto.PermissionResponseDto;
import com.beto.sip.infrastructure.web.dto.ApiResponse;

import java.util.List;

public interface RolePermissionApplicationService {
    ApiResponse<RoleResponseDto> createRole(CreateRoleCommand command);

    ApiResponse<RoleResponseDto> updateRole(UpdateRoleCommand command);

    ApiResponse<RoleResponseDto> deleteRole(Long id, Long updatedBy);

    ApiResponse<PermissionResponseDto> createPermission(CreatePermissionCommand command);

    ApiResponse<PermissionResponseDto> updatePermission(UpdatePermissionCommand command);

    ApiResponse<PermissionResponseDto> deletePermission(Long id, Long updatedBy);

    ApiResponse<Void> assignRoleToUser(AssignRoleToUserCommand command);

    ApiResponse<Void> unassignRoleFromUser(UnassignRoleFromUserCommand command);

    ApiResponse<Void> assignPermissionToRole(AssignPermissionToRoleCommand command);

    ApiResponse<Void> unassignPermissionFromRole(UnassignPermissionFromRoleCommand command);

    ApiResponse<List<RoleResponseDto>> getUserRoles(Long userId);

    ApiResponse<List<RoleResponseDto>> getRoles();

    ApiResponse<List<PermissionResponseDto>> getRolePermissions(Long roleId);

    ApiResponse<List<PermissionResponseDto>> getPermissions();
}
