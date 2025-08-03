package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.application.auth.service.RolePermissionApplicationService;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.AssignRoleToUserRequest;
import com.beto.sip.infrastructure.web.dto.CreateRoleRequest;
import com.beto.sip.infrastructure.web.dto.UpdateRoleRequest;
import com.beto.sip.infrastructure.web.mapper.RolePermissionRestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RoleResponseDto> createRole(@Validated @RequestBody CreateRoleRequest req) {
        return roleService.createRole(restMapper.toCommand(req));
    }

    @PutMapping
    public ApiResponse<RoleResponseDto> updateRole(@Validated @RequestBody UpdateRoleRequest req) {
        return roleService.updateRole(restMapper.toCommand(req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<RoleResponseDto> deleteRole(@PathVariable Long id, @RequestParam Long updatedBy) {
        UpdateRoleCommand cmd = new UpdateRoleCommand(id, null, null, "INACTIVE", updatedBy);
        return roleService.updateRole(cmd);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<RoleResponseDto>> getUserRoles(@PathVariable Long userId) {
        log.info("Request to fetch roles for user ID: {}", userId);
        return roleService.getUserRoles(userId);
    }

    @GetMapping
    public ApiResponse<List<RoleResponseDto>> getAllRoles() {
        log.info("Request to fetch all roles");
        return roleService.getRoles();
    }

    @PostMapping("/assign")
    public ApiResponse<Void> assignRoleToUser(@Validated @RequestBody AssignRoleToUserRequest req) {
        return roleService.assignRoleToUser(restMapper.toCommand(req));
    }
}