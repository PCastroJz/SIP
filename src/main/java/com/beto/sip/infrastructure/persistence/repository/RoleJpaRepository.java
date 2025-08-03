package com.beto.sip.infrastructure.persistence.repository;

import com.beto.sip.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r JOIN UserRoleEntity ur ON ur.roleId = r.id WHERE ur.userId = :userId AND ur.status = 'ACTIVE'")
    List<RoleEntity> findRolesByUserId(@Param("userId") Long userId);
}