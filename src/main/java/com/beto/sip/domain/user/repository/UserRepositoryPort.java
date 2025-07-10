package com.beto.sip.domain.user.repository;

import com.beto.sip.domain.user.User;
import com.beto.sip.domain.user.vo.UserId;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void delete(User user);

    boolean existsByUsername(String username);
}