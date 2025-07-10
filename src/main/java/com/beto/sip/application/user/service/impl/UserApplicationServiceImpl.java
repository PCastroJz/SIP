package com.beto.sip.application.user.service.impl;

import com.beto.sip.application.user.dto.*;
import com.beto.sip.application.user.service.UserApplicationService;
import com.beto.sip.domain.user.User;
import com.beto.sip.domain.user.repository.UserRepositoryPort;
import com.beto.sip.domain.user.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserRepositoryPort userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(CreateUserCommand cmd) {
        if (userRepo.existsByUsername(cmd.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = User.builder()
                .username(Username.of(cmd.getUsername()))
                .email(Email.of(cmd.getEmail()))
                .passwordHash(PasswordHash.of(passwordEncoder.encode(cmd.getPassword())))
                .status("ACTIVE")
                .createdAt(Instant.now())
                .build();
        User saved = userRepo.save(user);
        return toDto(saved);
    }

    @Override
    public UserResponseDto updateUser(UpdateUserCommand cmd) {
        User existing = userRepo.findById(UserId.of(cmd.getId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updated = User.builder()
                .id(existing.getId())
                .username(Username.of(cmd.getUsername()))
                .email(Email.of(cmd.getEmail()))
                .passwordHash(existing.getPasswordHash())
                .status(cmd.getStatus())
                .createdAt(existing.getCreatedAt())
                .updatedAt(Instant.now())
                .build();
        return toDto(userRepo.save(updated));
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userRepo.findById(UserId.of(id))
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public List<UserResponseDto> listUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepo.findById(UserId.of(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepo.delete(user);
    }

    private UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId().getValue())
                .username(user.getUsername().getValue())
                .email(user.getEmail().getValue())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}