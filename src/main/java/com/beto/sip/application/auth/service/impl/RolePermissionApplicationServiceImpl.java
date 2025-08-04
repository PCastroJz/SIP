package com.beto.sip.application.auth.service.impl;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.application.auth.service.RolePermissionApplicationService;
import com.beto.sip.domain.auth.Role;
import com.beto.sip.domain.auth.Permission;
import com.beto.sip.domain.auth.repository.RoleRepositoryPort;
import com.beto.sip.domain.user.exception.UserAlreadyExistsException;
import com.beto.sip.domain.auth.repository.PermissionRepositoryPort;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.ErrorCode;

import jakarta.transaction.Transactional;

import com.beto.sip.domain.auth.exception.RoleNotFoundException;
import com.beto.sip.domain.auth.exception.PermissionAlreadyExistsException;
import com.beto.sip.domain.auth.exception.PermissionNotFoundException;
import com.beto.sip.domain.auth.exception.RoleAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolePermissionApplicationServiceImpl implements RolePermissionApplicationService {

    private final RoleRepositoryPort roleRepo;
    private final PermissionRepositoryPort permRepo;

    @Override
    public ApiResponse<RoleResponseDto> createRole(CreateRoleCommand cmd) {
        log.info("Attempting to create role with name: {}", cmd.getName());

        if (roleRepo.findByName(cmd.getName()).isPresent()) {
            log.warn("Role creation failed: role {} already exists", cmd.getName());
            throw new RoleAlreadyExistsException("Role already exists");
        }

        Role role = Role.builder()
                .name(cmd.getName())
                .description(cmd.getDescription())
                .status("ACTIVE")
                .createdAt(Instant.now())
                .createdBy(cmd.getCreatedBy())
                .build();

        Role saved = roleRepo.save(role);
        log.info("Role created successfully with ID: {}", saved.getId());
        return ApiResponse.success(toRoleDto(saved), "ROLE_CREATED", HttpStatus.CREATED);
    }

    @Override
    public ApiResponse<PermissionResponseDto> createPermission(CreatePermissionCommand cmd) {
        log.info("Attempting to create permission with code: {}", cmd.getCode());

        if (permRepo.findByCode(cmd.getCode()).isPresent()) {
            log.warn("Permission creation failed: permission {} already exists", cmd.getCode());
            throw new PermissionAlreadyExistsException("Permission already exists");
        }


        Permission permission = Permission.builder()
                .name(cmd.getName())
                .code(cmd.getCode())
                .description(cmd.getDescription())
                .parentPermissionId(cmd.getParentPermissionId())
                .status("ACTIVE")
                .createdAt(Instant.now())
                .createdBy(cmd.getCreatedBy())
                .build();

        Permission saved = permRepo.save(permission);
        log.info("Permission created successfully with ID: {}", saved.getId());
        return ApiResponse.success(toPermissionDto(saved), "PERMISSION_CREATED", HttpStatus.CREATED);
    }

    @Override
    public ApiResponse<Void> assignRoleToUser(AssignRoleToUserCommand cmd) {
        log.info("Assigning role {} to user {}", cmd.getRoleId(), cmd.getUserId());
        roleRepo.assignRoleToUser(cmd.getUserId(), cmd.getRoleId(), cmd.getCreatedBy());
        return ApiResponse.success(null, "ROLE_ASSIGNED_TO_USER", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ApiResponse<Void> unassignRoleFromUser(UnassignRoleFromUserCommand cmd) {
        log.info("Deassigning role {} from user {}", cmd.getRoleId(), cmd.getUserId());
        roleRepo.unassignRoleFromUser(cmd.getUserId(), cmd.getRoleId());
        return ApiResponse.success(null, "ROLE_DEASSIGNED_FROM_USER", HttpStatus.OK);
    }

    @Override
    public ApiResponse<Void> assignPermissionToRole(AssignPermissionToRoleCommand cmd) {
        log.info("Assigning permission {} to role {}", cmd.getPermissionId(), cmd.getRoleId());
        permRepo.assignPermissionToRole(cmd.getRoleId(), cmd.getPermissionId(), cmd.getCreatedBy());
        return ApiResponse.success(null, "PERMISSION_ASSIGNED_TO_ROLE", HttpStatus.OK);
    }

    @Override
    public ApiResponse<Void> unassignPermissionFromRole(UnassignPermissionFromRoleCommand cmd) {
        log.info("Deassigning permission {} from role {}", cmd.getPermissionId(), cmd.getRoleId());
        permRepo.unassignPermissionFromRole(cmd.getRoleId(), cmd.getPermissionId());
        return ApiResponse.success(null, "PERMISSION_DEASSIGNED_FROM_ROLE", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<RoleResponseDto>> getUserRoles(Long userId) {
        log.info("Fetching roles for user {}", userId);
        List<RoleResponseDto> roles = roleRepo.findByUserId(userId)
                .stream()
                .map(this::toRoleDto)
                .collect(Collectors.toList());

        if (roles.isEmpty()) {
            log.warn("No roles found for user {}", userId);
            return ApiResponse.success(roles, ErrorCode.NO_USERS_FOUND.getCode(), HttpStatus.NO_CONTENT);
        }

        return ApiResponse.success(roles, "ROLES_FOUND", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<PermissionResponseDto>> getRolePermissions(Long roleId) {
        log.info("Fetching permissions for role {}", roleId);
        List<PermissionResponseDto> permissions = permRepo.findByRoleId(roleId)
                .stream()
                .map(this::toPermissionDto)
                .collect(Collectors.toList());

        if (permissions.isEmpty()) {
            log.warn("No permissions found for role {}", roleId);
            return ApiResponse.success(permissions, ErrorCode.NO_USERS_FOUND.getCode(), HttpStatus.NO_CONTENT);
        }

        return ApiResponse.success(permissions, "PERMISSIONS_FOUND", HttpStatus.OK);
    }

    @Override
    public ApiResponse<RoleResponseDto> updateRole(UpdateRoleCommand cmd) {
        log.info("Attempting to update role with ID: {}", cmd.getId());

        Role existing = roleRepo.findById(cmd.getId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        Role updated = existing.toBuilder()
                .name(cmd.getName() != null ? cmd.getName() : existing.getName())
                .description(cmd.getDescription() != null ? cmd.getDescription() : existing.getDescription())
                .status(cmd.getStatus() != null ? cmd.getStatus() : existing.getStatus())
                .updatedAt(Instant.now())
                .updatedBy(cmd.getUpdatedBy())
                .build();

        Role saved = roleRepo.save(updated);
        log.info("Role updated successfully with ID: {}", saved.getId());
        return ApiResponse.success(toRoleDto(saved), "ROLE_UPDATED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<RoleResponseDto> deleteRole(Long id, Long updatedBy) {
        log.info("Attempting to delete (soft) role with ID: {}", id);

        Role existing = roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        Role updated = existing.toBuilder()
                .status("INACTIVE")
                .updatedAt(Instant.now())
                .updatedBy(updatedBy)
                .build();

        Role saved = roleRepo.save(updated);
        log.info("Role soft-deleted successfully with ID: {}", saved.getId());
        return ApiResponse.success(toRoleDto(saved), "ROLE_DEACTIVATED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<PermissionResponseDto> updatePermission(UpdatePermissionCommand cmd) {
        log.info("Attempting to update permission with ID: {}", cmd.getId());

        Permission existing = permRepo.findById(cmd.getId())
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found"));

        Permission updated = existing.toBuilder()
                .name(cmd.getName() != null ? cmd.getName() : existing.getName())
                .code(cmd.getCode() != null ? cmd.getCode() : existing.getCode())
                .description(cmd.getDescription() != null ? cmd.getDescription() : existing.getDescription())
                .status(cmd.getStatus() != null ? cmd.getStatus() : existing.getStatus())
                .updatedAt(Instant.now())
                .updatedBy(cmd.getUpdatedBy())
                .build();

        Permission saved = permRepo.save(updated);
        log.info("Permission updated successfully with ID: {}", saved.getId());
        return ApiResponse.success(toPermissionDto(saved), "PERMISSION_UPDATED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<PermissionResponseDto> deletePermission(Long id, Long updatedBy) {
        log.info("Attempting to delete (soft) permission with ID: {}", id);

        Permission existing = permRepo.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException("Permission not found"));

        Permission updated = existing.toBuilder()
                .status("INACTIVE")
                .updatedAt(Instant.now())
                .updatedBy(updatedBy)
                .build();

        Permission saved = permRepo.save(updated);
        log.info("Permission soft-deleted successfully with ID: {}", saved.getId());
        return ApiResponse.success(toPermissionDto(saved), "PERMISSION_DEACTIVATED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<RoleResponseDto>> getRoles() {
        List<Role> roles = roleRepo.findAll();
        List<RoleResponseDto> roleDtos = roles.stream()
                .map(this::toRoleDto)
                .collect(Collectors.toList());
        return ApiResponse.success(roleDtos, "ROLES_FETCHED", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<PermissionResponseDto>> getPermissions() {
        List<Permission> permissions = permRepo.findAll();
        List<PermissionResponseDto> permissionDtos = permissions.stream()
                .map(this::toPermissionDto)
                .collect(Collectors.toList());
        return ApiResponse.success(permissionDtos, "PERMISSIONS_FETCHED", HttpStatus.OK);
    }

    // 🔹 Helpers
    private RoleResponseDto toRoleDto(Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .status(role.getStatus())
                .build();
    }

    private PermissionResponseDto toPermissionDto(Permission permission) {
        return PermissionResponseDto.builder()
                .id(permission.getId())
                .name(permission.getName())
                .code(permission.getCode())
                .description(permission.getDescription())
                .status(permission.getStatus())
                .build();
    }
}
