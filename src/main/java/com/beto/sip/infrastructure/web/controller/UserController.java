package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.user.dto.UserResponseDto;
import com.beto.sip.application.user.service.UserApplicationService;
import com.beto.sip.infrastructure.security.AuthorizationService;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.CreateUserRequest;
import com.beto.sip.infrastructure.web.dto.UpdatePasswordRequest;
import com.beto.sip.infrastructure.web.dto.UpdateUserRequest;
import com.beto.sip.infrastructure.web.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService appService;
    private final UserRestMapper restMapper;
    private final AuthorizationService authService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER_CREATE') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponseDto> createUser(@Validated @RequestBody CreateUserRequest req) {
        Long createdBy = authService.getCurrentUser().getId();
        return appService.createUser(restMapper.toCommand(req, createdBy));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable Long id) {
        authService.checkPermissionOrSelf("USER_VIEW", id);
        return appService.getById(id);
    }

    @GetMapping
    public ApiResponse<List<UserResponseDto>> listUsers() {
        if (authService.isAdmin() || authService.hasPermission("USER_VIEW")) {
            return appService.listUsers();
        }
        return ApiResponse.success(
                List.of(appService.getById(authService.getCurrentUser().getId()).getData()),
                "USERS_LISTED",
                HttpStatus.OK);
    }

    @PutMapping
    public ApiResponse<UserResponseDto> updateUser(@Validated @RequestBody UpdateUserRequest req) {
        authService.checkPermissionOrSelf("USER_UPDATE", req.getId());
        Long updatedBy = authService.getCurrentUser().getId();
        return appService.updateUser(restMapper.toCommand(req, updatedBy));
    }

    @PutMapping("/password")
    public ApiResponse<UserResponseDto> updatePassword(@Validated @RequestBody UpdatePasswordRequest req) {
        authService.checkPermissionOrSelf("USER_UPDATE", req.getId());
        Long updatedBy = authService.getCurrentUser().getId();
        return appService.updatePassword(restMapper.toCommand(req, updatedBy));
    }
}