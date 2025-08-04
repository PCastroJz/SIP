package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.application.auth.service.RolePermissionApplicationService;
import com.beto.sip.infrastructure.security.CustomUserPrincipal;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.AssignRoleToUserRequest;
import com.beto.sip.infrastructure.web.dto.CreateRoleRequest;
import com.beto.sip.infrastructure.web.dto.UnassignRoleFromUserRequest;
import com.beto.sip.infrastructure.web.dto.UpdateRoleRequest;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RolePermissionApplicationService roleService;

    private final RolePermissionRestMapper restMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CREATE') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RoleResponseDto> createRole(@Validated @RequestBody CreateRoleRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long createdBy = principal.getId();
        return roleService.createRole(restMapper.toCommand(req, createdBy));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<RoleResponseDto> updateRole(@Validated @RequestBody UpdateRoleRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long updatedBy = principal.getId();
        return roleService.updateRole(restMapper.toCommand(req, updatedBy));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<RoleResponseDto> deleteRole(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long updatedBy = principal.getId();
        UpdateRoleCommand cmd = new UpdateRoleCommand(id, null, null, "INACTIVE", updatedBy);
        return roleService.updateRole(cmd);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW') or hasRole('ADMIN')")
    public ApiResponse<List<RoleResponseDto>> getUserRoles(@PathVariable Long userId) {
        log.info("Request to fetch roles for user ID: {}", userId);
        return roleService.getUserRoles(userId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_VIEW') or hasRole('ADMIN')")
    public ApiResponse<List<RoleResponseDto>> getAllRoles() {
        log.info("Request to fetch all roles");
        return roleService.getRoles();
    }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('ROLE_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<Void> assignRoleToUser(@Validated @RequestBody AssignRoleToUserRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        Long createdBy = principal.getId();
        return roleService.assignRoleToUser(restMapper.toCommand(req, createdBy));
    }

    @DeleteMapping("/unassign")
    @PreAuthorize("hasAuthority('ROLE_UPDATE') or hasRole('ADMIN')")
    public ApiResponse<Void> unassignRoleFromUser(@Validated @RequestBody UnassignRoleFromUserRequest req) {
        return roleService.unassignRoleFromUser(
                new UnassignRoleFromUserCommand(req.getUserId(), req.getRoleId()));
    }
}