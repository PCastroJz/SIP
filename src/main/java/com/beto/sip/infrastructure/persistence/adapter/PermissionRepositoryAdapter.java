package com.beto.sip.infrastructure.persistence.adapter;

import com.beto.sip.domain.auth.Permission;
import com.beto.sip.domain.auth.Role;
import com.beto.sip.domain.auth.repository.PermissionRepositoryPort;
import com.beto.sip.infrastructure.persistence.entity.RolePermissionEntity;
import com.beto.sip.infrastructure.persistence.mapper.PermissionPersistenceMapper;
import com.beto.sip.infrastructure.persistence.repository.PermissionJpaRepository;
import com.beto.sip.infrastructure.persistence.repository.RolePermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final PermissionJpaRepository permRepo;
    private final RolePermissionJpaRepository rolePermRepo;
    private final PermissionPersistenceMapper mapper;

    @Override
    public Permission save(Permission permission) {
        var saved = permRepo.save(mapper.toEntity(permission));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Permission> findByCode(String code) {
        return permRepo.findByCode(code).map(mapper::toDomain);
    }

    @Override
    public List<Permission> findByRoleId(Long roleId) {
        return permRepo.findPermissionsByRoleId(roleId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Permission> findAll() {
        return permRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void assignPermissionToRole(Long roleId, Long permissionId, Long createdBy) {
        rolePermRepo.save(
                RolePermissionEntity.builder()
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .status("ACTIVE")
                        .createdAt(Instant.now())
                        .createdBy(createdBy)
                        .build());
    }

    @Override
    public void unassignPermissionFromRole(Long roleId, Long permissionId) {
        rolePermRepo.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }
}