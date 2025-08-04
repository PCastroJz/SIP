package com.beto.sip.infrastructure.persistence.adapter;

import com.beto.sip.domain.auth.Role;
import com.beto.sip.domain.auth.repository.RoleRepositoryPort;
import com.beto.sip.infrastructure.persistence.entity.UserRoleEntity;
import com.beto.sip.infrastructure.persistence.mapper.RolePersistenceMapper;
import com.beto.sip.infrastructure.persistence.repository.RoleJpaRepository;
import com.beto.sip.infrastructure.persistence.repository.UserRoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleRepo;
    private final UserRoleJpaRepository userRoleRepo;
    private final RolePersistenceMapper mapper;

    @Override
    public Role save(Role role) {
        var saved = roleRepo.save(mapper.toEntity(role));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return roleRepo.findRolesByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void assignRoleToUser(Long userId, Long roleId, Long createdBy) {
        userRoleRepo.save(
                UserRoleEntity.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .status("ACTIVE")
                        .createdAt(Instant.now())
                        .createdBy(createdBy)
                        .build());
    }

    @Override
    public void unassignRoleFromUser(Long userId, Long roleId) {
        userRoleRepo.deleteByUserIdAndRoleId(userId, roleId);
    }

}