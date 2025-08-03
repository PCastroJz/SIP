package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.application.auth.service.RolePermissionApplicationService;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.AssignPermissionToRoleRequest;
import com.beto.sip.infrastructure.web.dto.CreatePermissionRequest;
import com.beto.sip.infrastructure.web.dto.UpdatePermissionRequest;
import com.beto.sip.infrastructure.web.mapper.RolePermissionRestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PermissionResponseDto> createPermission(@Validated @RequestBody CreatePermissionRequest req) {
        return roleService.createPermission(restMapper.toCommand(req));
    }

    @PutMapping
    public ApiResponse<PermissionResponseDto> updatePermission(@Validated @RequestBody UpdatePermissionRequest req) {
        return roleService.updatePermission(restMapper.toCommand(req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<PermissionResponseDto> deletePermission(@PathVariable Long id, @RequestParam Long updatedBy) {
        UpdatePermissionCommand cmd = new UpdatePermissionCommand(id, null, null, null, "INACTIVE", updatedBy);
        return roleService.updatePermission(cmd);
    }

    @GetMapping("/role/{roleId}")
    public ApiResponse<List<PermissionResponseDto>> getRolePermissions(@PathVariable Long roleId) {
        log.info("Request to fetch permissions for role ID: {}", roleId);
        return roleService.getRolePermissions(roleId);
    }

    @GetMapping
    public ApiResponse<List<PermissionResponseDto>> getAllPermissions() {
        log.info("Request to fetch all permissions");
        return roleService.getPermissions();
    }

    @PostMapping("/assign")
    public ApiResponse<Void> assignPermissionToRole(@Validated @RequestBody AssignPermissionToRoleRequest req) {
        return roleService.assignPermissionToRole(restMapper.toCommand(req));
    }
}