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
    public CreateUserCommand toCommand(CreateUserRequest req, Long createdBy) {
        return new CreateUserCommand(req.getUsername(), req.getEmail(), req.getPassword(), createdBy);
    }

    public UpdateUserCommand toCommand(UpdateUserRequest req, Long updatedBy) {
        return new UpdateUserCommand(req.getId(), req.getUsername(), req.getStatus(), updatedBy);
    }

    public UpdateUserPasswordCommand toCommand(UpdatePasswordRequest req, Long updatedBy) {
        return new UpdateUserPasswordCommand(req.getId(), req.getPassword(), updatedBy);
    }
}
