package com.localservice.bookingplatform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContext;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil,
                         CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                System.out.println("JWT extractEmail failed: " + e.getMessage());
            }
        }

        if (email != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {
            try {
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                    SecurityContext securityContext =
                            SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authToken);
                    SecurityContextHolder.setContext(securityContext);
                    System.out.println("Authentication set for: " + email);
                } else {
                    System.out.println("Token validation failed for: " + email);
                }
            } catch (Exception e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}