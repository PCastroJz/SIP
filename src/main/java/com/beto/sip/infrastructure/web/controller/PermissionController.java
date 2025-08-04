package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.application.auth.service.RolePermissionApplicationService;
import com.beto.sip.infrastructure.security.CustomUserPrincipal;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.AssignPermissionToRoleRequest;
import com.beto.sip.infrastructure.web.dto.CreatePermissionRequest;
import com.beto.sip.infrastructure.web.dto.UnassignPermissionFromRoleRequest;
import com.beto.sip.infrastructure.web.dto.UpdatePermissionRequest;
import com.beto.sip.infrastructure.web.mapper.RolePermissionRestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final RolePermissionApplicationService roleService;

    private final RolePermissionRestMapper restMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('PERMISSION_CREATE') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PermissionResponseDto> createPermission(@Validated @RequestBody CreatePermissionRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long createdBy = principal.getId();
        return roleService.createPermission(restMapper.toCommand(req, createdBy));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<PermissionResponseDto> updatePermission(@Validated @RequestBody UpdatePermissionRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long updatedBy = principal.getId();

        return roleService.updatePermission(restMapper.toCommand(req, updatedBy));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<PermissionResponseDto> deletePermission(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long updatedBy = principal.getId();
        UpdatePermissionCommand cmd = new UpdatePermissionCommand(id, null, null, null, "INACTIVE", updatedBy);
        return roleService.updatePermission(cmd);
    }

    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAuthority('PERMISSION_VIEW') or hasRole('ADMIN')")
    public ApiResponse<List<PermissionResponseDto>> getRolePermissions(@PathVariable Long roleId) {
        log.info("Request to fetch permissions for role ID: {}", roleId);
        return roleService.getRolePermissions(roleId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PERMISSION_VIEW') or hasRole('ADMIN')")
    public ApiResponse<List<PermissionResponseDto>> getAllPermissions() {
        log.info("Request to fetch all permissions");
        return roleService.getPermissions();
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<Void> assignPermissionToRole(@Validated @RequestBody AssignPermissionToRoleRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long createdBy = principal.getId();
        return roleService.assignPermissionToRole(restMapper.toCommand(req, createdBy));
    }

    @DeleteMapping("/unassign")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<Void> unassignPermissionFromRole(@Validated @RequestBody UnassignPermissionFromRoleRequest req) {
        return roleService.unassignPermissionFromRole(
                new UnassignPermissionFromRoleCommand(req.getRoleId(), req.getPermissionId()));
    }
}