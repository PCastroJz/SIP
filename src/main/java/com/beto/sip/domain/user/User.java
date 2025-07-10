package com.beto.sip.domain.user;

import com.beto.sip.domain.shared.AuditableEntity;
import com.beto.sip.domain.user.vo.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class User extends AuditableEntity {
    private final UserId id;
    private final Username username;
    private final Email email;
    private final PasswordHash passwordHash;
    private final String status;

}