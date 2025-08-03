package com.beto.sip.infrastructure.persistence.mapper;

import com.beto.sip.domain.auth.Role;
import com.beto.sip.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RolePersistenceMapper {

    public Role toDomain(RoleEntity e) {
        if (e == null)
            return null;
        return Role.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .createdBy(e.getCreatedBy())
                .updatedAt(e.getUpdatedAt())
                .updatedBy(e.getUpdatedBy())
                .build();
    }

    public RoleEntity toEntity(Role r) {
        if (r == null)
            return null;
        return RoleEntity.builder()
                .id(r.getId())
                .name(r.getName())
                .description(r.getDescription())
                .status(r.getStatus())
                .createdAt(r.getCreatedAt())
                .createdBy(r.getCreatedBy())
                .updatedAt(r.getUpdatedAt())
                .updatedBy(r.getUpdatedBy())
                .build();
    }
}