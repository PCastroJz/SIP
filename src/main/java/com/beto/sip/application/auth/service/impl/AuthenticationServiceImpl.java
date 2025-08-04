package com.beto.sip.application.auth.service.impl;

import com.beto.sip.application.auth.service.AuthenticationService;
import com.beto.sip.domain.auth.Permission;
import com.beto.sip.domain.auth.Role;
import com.beto.sip.domain.auth.exception.InvalidCredentialsException;
import com.beto.sip.domain.auth.repository.PermissionRepositoryPort;
import com.beto.sip.domain.auth.repository.RoleRepositoryPort;
import com.beto.sip.domain.user.User;
import com.beto.sip.domain.user.repository.UserRepositoryPort;
import com.beto.sip.infrastructure.security.JwtUtil;
import com.beto.sip.infrastructure.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepositoryPort userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepositoryPort roleRepo;
    private final PermissionRepositoryPort permissionRepo;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        log.info("Attempting login for username: {}", request.getUsername());

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash().getValue())) {
            throw new InvalidCredentialsException();
        }

        List<Role> roles = roleRepo.findByUserId(user.getId().getValue());
        List<String> roleNames = roles.stream().map(Role::getName).toList();

        List<String> permissions = roles.stream()
                .flatMap(role -> permissionRepo.findByRoleId(role.getId()).stream())
                .map(Permission::getCode)
                .distinct()
                .toList();

        String token = jwtUtil.generateToken(user, roleNames, permissions);

        return ApiResponse.success(
                new LoginResponse(token, user.getUsername().getValue()),
                "LOGIN_SUCCESS",
                HttpStatus.OK);
    }
}