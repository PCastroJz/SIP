package com.beto.sip.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserPrincipal {
    private Long id;
    private String username;
}