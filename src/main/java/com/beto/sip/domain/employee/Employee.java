package com.beto.sip.domain.employee;

import com.beto.sip.domain.shared.AuditableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder (toBuilder = true)
public class Employee extends AuditableEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String employeeNumber;
    private String position;
    private Long userId;
    private String status;
}