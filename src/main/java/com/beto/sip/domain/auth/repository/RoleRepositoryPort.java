package com.beto.sip.domain.auth.repository;

import com.beto.sip.domain.auth.Role;
import java.util.List;
import java.util.Optional;

public interface RoleRepositoryPort {
    Role save(Role role);

    Optional<Role> findById(Long id);

    List<Role> findByUserId(Long userId);

    List<Role> findAll();

    void assignRoleToUser(Long userId, Long roleId, Long createdBy);
}
