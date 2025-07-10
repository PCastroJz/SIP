package com.beto.sip.application.user.service;

import com.beto.sip.application.user.dto.CreateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserCommand;
import com.beto.sip.application.user.dto.UserResponseDto;
import java.util.List;

public interface UserApplicationService {
    UserResponseDto createUser(CreateUserCommand command);

    UserResponseDto updateUser(UpdateUserCommand command);

    UserResponseDto getById(Long id);

    List<UserResponseDto> listUsers();

    void deleteUser(Long id);
}