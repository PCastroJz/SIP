package com.beto.sip.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        if (jwtUtil.validateToken(token)) {
            Map<String, Object> claims = jwtUtil.getAllClaims(token);

            Number userIdNumber = (Number) claims.get("userId");
            Long userId = userIdNumber != null ? userIdNumber.longValue() : null;
            String username = (String) claims.get("sub");
            List<String> roles = (List<String>) claims.get("roles");
            List<String> permissions = (List<String>) claims.get("permissions");

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (roles != null) {
                authorities.addAll(roles.stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .toList());
            }
            if (permissions != null) {
                authorities.addAll(permissions.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(userId, username), null,
                    authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("User {} autenticado con roles {}", username, roles);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
