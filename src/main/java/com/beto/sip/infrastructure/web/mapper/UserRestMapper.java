package com.beto.sip.infrastructure.web.mapper;

import com.beto.sip.application.user.dto.CreateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserCommand;
import com.beto.sip.application.user.dto.UpdateUserPasswordCommand;
import com.beto.sip.infrastructure.web.dto.CreateUserRequest;
import com.beto.sip.infrastructure.web.dto.UpdatePasswordRequest;
import com.beto.sip.infrastructure.web.dto.UpdateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
    public CreateUserCommand toCommand(CreateUserRequest req) {
        return new CreateUserCommand(req.getUsername(), req.getEmail(), req.getPassword(), req.getCreatedBy());
    }

    public UpdateUserCommand toCommand(UpdateUserRequest req) {
        return new UpdateUserCommand(req.getId(), req.getUsername(), req.getStatus(), req.getUpdatedBy());
    }

    public UpdateUserPasswordCommand toCommand(UpdatePasswordRequest req) {
        return new UpdateUserPasswordCommand(req.getId(), req.getPassword(), req.getUpdatedBy());
    }
}
