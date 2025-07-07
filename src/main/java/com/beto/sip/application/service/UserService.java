package com.beto.sip.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beto.sip.infrastructure.persistence.entity.UserEntity;
import com.beto.sip.infrastructure.persistence.repository.UserRepository;

import java.util.Optional;
import java.util.List;

/**
 * Casos de uso relacionados con usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crear un nuevo usuario.
     */
    @Transactional
    public UserEntity createUser(String username, String passwordHash, String email) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setEmail(email);
        return userRepository.save(user);
    }

    /**
     * Buscar un usuario por nombre de usuario.
     */
    @Transactional(readOnly = true)
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Listar todos los usuarios activos.
     */
    @Transactional(readOnly = true)
    public List<UserEntity> listAll() {
        return userRepository.findAll();
    }
}
