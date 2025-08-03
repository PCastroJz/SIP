package com.beto.sip.domain.auth;

import com.beto.sip.domain.shared.AuditableEntity;
import com.beto.sip.domain.auth.Permission;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Role extends AuditableEntity {
    private Long id;
    private String name;
    private String description;
    private String status;
    private List<Permission> permissions;
}
