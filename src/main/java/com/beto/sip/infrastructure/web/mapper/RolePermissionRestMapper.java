package com.beto.sip.infrastructure.web.mapper;

import com.beto.sip.application.auth.dto.*;
import com.beto.sip.infrastructure.web.dto.*;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionRestMapper {

    public CreateRoleCommand toCommand(CreateRoleRequest req, Long createdBy) {
        return new CreateRoleCommand(req.getName(), req.getDescription(), createdBy);
    }

    public UpdateRoleCommand toCommand(UpdateRoleRequest req, Long updatedBy) {
        return new UpdateRoleCommand(req.getId(), req.getName(), req.getDescription(),
                req.getStatus(), updatedBy);
    }

    public CreatePermissionCommand toCommand(CreatePermissionRequest req, Long createdBy) {
        return new CreatePermissionCommand(req.getName(), req.getCode(), req.getDescription(),
                req.getParentPermissionId(), createdBy);
    }

    public UpdatePermissionCommand toCommand(UpdatePermissionRequest req, Long updatedBy) {
        return new UpdatePermissionCommand(req.getId(), req.getName(), req.getCode(),
                req.getDescription(), req.getStatus(), updatedBy);
    }

    public AssignRoleToUserCommand toCommand(AssignRoleToUserRequest req, Long createdBy) {
        return new AssignRoleToUserCommand(req.getUserId(), req.getRoleId(), createdBy);
    }

    public AssignPermissionToRoleCommand toCommand(AssignPermissionToRoleRequest req, Long createdBy) {
        return new AssignPermissionToRoleCommand(req.getRoleId(), req.getPermissionId(), createdBy);
    }

    public UnassignPermissionFromRoleCommand toCommand(UnassignPermissionFromRoleRequest req) {
        return new UnassignPermissionFromRoleCommand(req.getRoleId(), req.getPermissionId());
    }

    public UnassignRoleFromUserCommand toCommand(UnassignRoleFromUserRequest req) {
        return new UnassignRoleFromUserCommand(req.getUserId(), req.getRoleId());
    }
}
