package com.beto.sip.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beto.sip.infrastructure.persistence.entity.UserEntity;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad UserEntity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
