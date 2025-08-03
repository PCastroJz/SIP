package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.user.dto.UserResponseDto;
import com.beto.sip.application.user.service.UserApplicationService;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.CreateUserRequest;
import com.beto.sip.infrastructure.web.dto.UpdatePasswordRequest;
import com.beto.sip.infrastructure.web.dto.UpdateUserRequest;
import com.beto.sip.infrastructure.web.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService appService;
    private final UserRestMapper restMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponseDto> createUser(@Validated @RequestBody CreateUserRequest req) {
        return appService.createUser(restMapper.toCommand(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUser(@PathVariable Long id) {
        return appService.getById(id);
    }

    @GetMapping
    public ApiResponse<List<UserResponseDto>> listUsers() {
        return appService.listUsers();
    }

    @PutMapping
    public ApiResponse<UserResponseDto> updateUser(@Validated @RequestBody UpdateUserRequest req) {
        return appService.updateUser(restMapper.toCommand(req));
    }

    @PutMapping("/password")
    public ApiResponse<UserResponseDto> updatePassword(@Validated @RequestBody UpdatePasswordRequest req) {
        return appService.updatePassword(restMapper.toCommand(req));
    }
    
}