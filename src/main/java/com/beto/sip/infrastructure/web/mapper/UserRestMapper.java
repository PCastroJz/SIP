package com.beto.sip.infrastructure.web.mapper;

import com.beto.sip.application.user.dto.CreateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserCommand;
import com.beto.sip.infrastructure.web.dto.CreateUserRequest;
import com.beto.sip.infrastructure.web.dto.UpdateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
    public CreateUserCommand toCommand(CreateUserRequest req) {
        return new CreateUserCommand(req.getUsername(), req.getEmail(), req.getPassword());
    }

    public UpdateUserCommand toCommand(UpdateUserRequest req) {
        return new UpdateUserCommand(req.getId(), req.getUsername(), req.getEmail(), req.getStatus());
    }
}
