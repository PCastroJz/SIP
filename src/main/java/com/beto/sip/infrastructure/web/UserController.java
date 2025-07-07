package com.beto.sip.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.beto.sip.application.service.UserService;
import com.beto.sip.infrastructure.persistence.entity.UserEntity;

import java.util.List;

/**
 * API REST para operaciones sobre usuarios.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static record CreateUserRequest(String username, String password, String email) {
    }

    public static record UserResponse(Long id, String username, String email) {
        public static UserResponse fromEntity(UserEntity u) {
            return new UserResponse(u.getId(), u.getUsername(), u.getEmail());
        }
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest req) {
        var user = userService.createUser(req.username(), req.password(), req.email());
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {
        var list = userService.listAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(UserResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
