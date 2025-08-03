package com.beto.sip.infrastructure.persistence.mapper;

import com.beto.sip.domain.auth.Permission;
import com.beto.sip.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionPersistenceMapper {

    public Permission toDomain(PermissionEntity e) {
        if (e == null)
            return null;
        return Permission.builder()
                .id(e.getId())
                .name(e.getName())
                .code(e.getCode())
                .description(e.getDescription())
                .parentPermissionId(e.getParentPermissionId())
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .createdBy(e.getCreatedBy())
                .updatedAt(e.getUpdatedAt())
                .updatedBy(e.getUpdatedBy())
                .build();
    }

    public PermissionEntity toEntity(Permission p) {
        if (p == null)
            return null;
        return PermissionEntity.builder()
                .id(p.getId())
                .name(p.getName())
                .code(p.getCode())
                .description(p.getDescription())
                .parentPermissionId(p.getParentPermissionId())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .createdBy(p.getCreatedBy())
                .updatedAt(p.getUpdatedAt())
                .updatedBy(p.getUpdatedBy())
                .build();
    }
}