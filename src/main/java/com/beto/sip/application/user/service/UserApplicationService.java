package com.beto.sip.application.user.service;

import com.beto.sip.application.user.dto.CreateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserPasswordCommand;
import com.beto.sip.application.user.dto.UserResponseDto;
import com.beto.sip.infrastructure.web.dto.ApiResponse;

import java.util.List;

import org.hibernate.sql.Update;

public interface UserApplicationService {
    ApiResponse<UserResponseDto> createUser(CreateUserCommand command);

    ApiResponse<UserResponseDto> updateUser(UpdateUserCommand command);

    ApiResponse<UserResponseDto> getById(Long id);

    ApiResponse<List<UserResponseDto>> listUsers();

    ApiResponse<UserResponseDto> updatePassword(UpdateUserPasswordCommand command);
}