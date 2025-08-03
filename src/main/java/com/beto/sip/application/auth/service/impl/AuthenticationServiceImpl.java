package com.beto.sip.application.auth.service.impl;

import com.beto.sip.application.auth.service.AuthenticationService;
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

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        log.info("Attempting login for username: {}", request.getUsername());

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash().getValue())) {
            throw new RuntimeException("Invalid username or password");
        }

        List<String> roles = List.of("USER");

        String token = jwtUtil.generateToken(user.getUsername().getValue(), roles);

        return ApiResponse.success(new LoginResponse(token, user.getUsername().getValue()),
                "LOGIN_SUCCESS", HttpStatus.OK);
    }
}