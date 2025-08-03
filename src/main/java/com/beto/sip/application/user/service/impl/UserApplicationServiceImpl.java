package com.beto.sip.application.user.service.impl;

import com.beto.sip.application.user.dto.*;
import com.beto.sip.application.user.service.UserApplicationService;
import com.beto.sip.domain.user.User;
import com.beto.sip.domain.user.exception.UserAlreadyExistsException;
import com.beto.sip.domain.user.exception.UserNotFoundException;
import com.beto.sip.domain.user.repository.UserRepositoryPort;
import com.beto.sip.infrastructure.web.dto.ApiResponse;
import com.beto.sip.infrastructure.web.dto.ErrorCode;
import com.beto.sip.domain.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserRepositoryPort userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<UserResponseDto> createUser(CreateUserCommand cmd) {
        log.info("Attempting to create user with username: {}", cmd.getUsername());

        if (userRepo.existsByUsername(cmd.getUsername())) {
            log.warn("User creation failed: username {} already exists", cmd.getUsername());
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .username(Username.of(cmd.getUsername()))
                .email(Email.of(cmd.getEmail()))
                .passwordHash(PasswordHash.of(passwordEncoder.encode(cmd.getPassword())))
                .status("ACTIVE")
                .createdAt(Instant.now())
                .createdBy(cmd.getCreatedBy())
                .build();

        User saved = userRepo.save(user);
        log.info("User created successfully with ID: {}", saved.getId().getValue());
        return ApiResponse.success(toDto(saved), ErrorCode.USER_EXISTS.getCode(), HttpStatus.CREATED);
    }

    @Override
    public ApiResponse<UserResponseDto> updateUser(UpdateUserCommand cmd) {
        log.info("Attempting to update user with ID: {}", cmd.getId());

        User existing = userRepo.findById(UserId.of(cmd.getId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (cmd.getStatus() == "INACTIVE") {
            User updated = existing.toBuilder()
                    .status("INACTIVE")
                    .updatedAt(Instant.now())
                    .updatedBy(cmd.getUpdatedBy())
                    .build();

            User savedUser = userRepo.save(updated);
            log.info("User deactivated successfully with ID: {}", savedUser.getId().getValue());
            return ApiResponse.success(toDto(savedUser), "USER_DEACTIVATED", HttpStatus.OK);
        } else {

            User updated = existing.toBuilder()
                    .username(cmd.getUsername() != null ? Username.of(cmd.getUsername()) : existing.getUsername())
                    .status(cmd.getStatus() != null ? cmd.getStatus() : existing.getStatus())
                    .updatedAt(Instant.now())
                    .updatedBy(cmd.getUpdatedBy())
                    .build();

            User saved = userRepo.save(updated);
            log.info("User updated successfully with ID: {}", saved.getId().getValue());
            return ApiResponse.success(toDto(saved), "USER_UPDATED", HttpStatus.OK);
        }
    }

    @Override
    public ApiResponse<UserResponseDto> getById(Long id) {
        log.info("Fetching user by ID: {}", id);

        User user = userRepo.findById(UserId.of(id))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return ApiResponse.success(toDto(user), "USER_FOUND", HttpStatus.OK);
    }

    @Override
    public ApiResponse<List<UserResponseDto>> listUsers() {
        log.info("Listing all users");

        List<UserResponseDto> users = userRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            log.warn("No users found");
            return ApiResponse.success(users, ErrorCode.NO_USERS_FOUND.getCode(), HttpStatus.NO_CONTENT);
        }

        return ApiResponse.success(users, "USERS_LISTED", HttpStatus.OK);
    }

    public ApiResponse<UserResponseDto> updatePassword(UpdateUserPasswordCommand cmd) {
        log.info("Updating password for user ID: {}", cmd.getId());

        User user = userRepo.findById(UserId.of(cmd.getId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String newPasswordHash = passwordEncoder.encode(cmd.getPassword());
        User updatedUser = user.toBuilder()
                .passwordHash(PasswordHash.of(newPasswordHash))
                .updatedAt(Instant.now())
                .updatedBy(cmd.getUpdatedBy())
                .build();

        User savedUser = userRepo.save(updatedUser);
        log.info("Password updated successfully for user ID: {}", savedUser.getId().getValue());
        return ApiResponse.success(toDto(savedUser), "PASSWORD_UPDATED", HttpStatus.OK);
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId() != null ? user.getId().getValue() : null)
                .username(user.getUsername() != null ? user.getUsername().getValue() : null)
                .email(user.getEmail() != null ? user.getEmail().getValue() : null)
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }
}