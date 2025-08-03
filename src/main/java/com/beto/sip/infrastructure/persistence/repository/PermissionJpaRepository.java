package com.beto.sip.infrastructure.persistence.repository;

import com.beto.sip.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {

    @Query("SELECT p FROM PermissionEntity p JOIN RolePermissionEntity rp ON rp.permissionId = p.id WHERE rp.roleId = :roleId AND rp.status = 'ACTIVE'")
    List<PermissionEntity> findPermissionsByRoleId(@Param("roleId") Long roleId);
}