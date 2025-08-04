package com.beto.sip.infrastructure.persistence.repository;

import com.beto.sip.infrastructure.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionEntity, RolePermissionEntity.PK> {
    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
