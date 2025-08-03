package com.beto.sip.domain.user;

import java.util.List;

import com.beto.sip.domain.auth.Role;
import com.beto.sip.domain.shared.AuditableEntity;
import com.beto.sip.domain.user.vo.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends AuditableEntity {
    private UserId id;
    private Username username;
    private Email email;
    private PasswordHash passwordHash;
    private String status;
    private List<Role> roles;
}
