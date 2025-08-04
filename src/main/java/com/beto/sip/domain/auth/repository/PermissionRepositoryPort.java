package com.beto.sip.domain.auth.repository;

import com.beto.sip.domain.auth.Permission;
import java.util.List;
import java.util.Optional;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    Optional<Permission> findByCode(String code);

    List<Permission> findByRoleId(Long roleId);

    List<Permission> findAll();

    void assignPermissionToRole(Long roleId, Long permissionId, Long createdBy);

    void unassignPermissionFromRole(Long roleId, Long permissionId);
}