package com.beto.sip.infrastructure.security;

import com.beto.sip.domain.auth.exception.CustomAccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService {

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public CustomUserPrincipal getCurrentUser() {
        return (CustomUserPrincipal) getAuth().getPrincipal();
    }

    public boolean isAdmin() {
        return getAuth().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean hasPermission(String permission) {
        return getAuth().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(permission));
    }

    public void checkPermissionOrSelf(String permission, Long targetUserId) {
        CustomUserPrincipal current = getCurrentUser();
        boolean isAdmin = isAdmin();
        boolean hasPerm = hasPermission(permission);

        if (!isAdmin && !hasPerm && !targetUserId.equals(current.getId())) {
            throw new CustomAccessDeniedException("No tienes permiso para acceder a este recurso");
        }
    }

    public void checkPermissionOrSelf(String permission) {
        CustomUserPrincipal current = getCurrentUser();
        boolean isAdmin = isAdmin();
        boolean hasPerm = hasPermission(permission);

        if (!isAdmin && !hasPerm) {
            throw new CustomAccessDeniedException("No tienes permiso para acceder a este recurso");
        }
    }
}
