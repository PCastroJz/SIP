package com.beto.sip.domain.auth;

import com.beto.sip.domain.shared.AuditableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Permission extends AuditableEntity {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Long parentPermissionId;
    private String status;
}
