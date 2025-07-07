package com.beto.sip.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.*;

/**
 * Entidad que representa a un usuario del sistema con Lombok.
 */
@Entity
@Table(name = "users")
@Where(clause = "status = 'ACTIVE'")
@SQLDelete(sql = "UPDATE users SET status = 'INACTIVE' WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;
}