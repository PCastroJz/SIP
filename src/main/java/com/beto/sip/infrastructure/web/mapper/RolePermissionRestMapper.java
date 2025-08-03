package com.beto.sip.infrastructure.web.mapper;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.infrastructure.web.dto.*;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionRestMapper {

    public CreateRoleCommand toCommand(CreateRoleRequest req) {
        return new CreateRoleCommand(req.getName(), req.getDescription(), req.getCreatedBy());
    }

    public UpdateRoleCommand toCommand(UpdateRoleRequest req) {
        return new UpdateRoleCommand(req.getId(), req.getName(), req.getDescription(),
                req.getStatus(), req.getUpdatedBy());
    }

    public CreatePermissionCommand toCommand(CreatePermissionRequest req) {
        return new CreatePermissionCommand(req.getName(), req.getCode(), req.getDescription(),
                req.getParentPermissionId(), req.getCreatedBy());
    }

    public UpdatePermissionCommand toCommand(UpdatePermissionRequest req) {
        return new UpdatePermissionCommand(req.getId(), req.getName(), req.getCode(),
                req.getDescription(), req.getStatus(), req.getUpdatedBy());
    }

    public AssignRoleToUserCommand toCommand(AssignRoleToUserRequest req) {
        return new AssignRoleToUserCommand(req.getUserId(), req.getRoleId(), req.getCreatedBy());
    }

    public AssignPermissionToRoleCommand toCommand(AssignPermissionToRoleRequest req) {
        return new AssignPermissionToRoleCommand(req.getRoleId(), req.getPermissionId(), req.getCreatedBy());
    }
}
