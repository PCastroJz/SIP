package com.beto.sip.infrastructure.persistence.mapper;

import com.beto.sip.domain.user.User;
import com.beto.sip.domain.user.vo.*;
import com.beto.sip.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {
    public User toDomain(UserEntity e) {
        return User.builder()
                .id(UserId.of(e.getId()))
                .username(Username.of(e.getUsername()))
                .email(Email.of(e.getEmail()))
                .passwordHash(PasswordHash.of(e.getPasswordHash()))
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public UserEntity toEntity(User u) {
        return UserEntity.builder()
                .id(u.getId() != null ? u.getId().getValue() : null)
                .username(u.getUsername().getValue())
                .email(u.getEmail().getValue())
                .passwordHash(u.getPasswordHash().getValue())
                .status(u.getStatus())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }
}
