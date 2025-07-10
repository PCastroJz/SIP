package com.beto.sip.infrastructure.web.controller;

import com.beto.sip.application.user.dto.UserResponseDto;
import com.beto.sip.application.user.service.UserApplicationService;
import com.beto.sip.infrastructure.web.dto.CreateUserRequest;
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
    public UserResponseDto createUser(@Validated @RequestBody CreateUserRequest req) {
        return appService.createUser(restMapper.toCommand(req));
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return appService.getById(id);
    }

    @GetMapping
    public List<UserResponseDto> listUsers() {
        return appService.listUsers();
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
            @Validated @RequestBody UpdateUserRequest req) {
        return appService.updateUser(restMapper.toCommand(req));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        appService.deleteUser(id);
    }
}