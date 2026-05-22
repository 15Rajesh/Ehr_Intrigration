package com.ehr_integration_platform.security;

import com.ehr_integration_platform.security.service.CustomUserDetailsService;
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

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userService;

    public JwtFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService userService
    ) {

        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip Swagger URLs
        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui.html")) {

            chain.doFilter(request, response);
            return;
        }

        final String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Extract Token
        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            try {

                username =
                        jwtUtil.extractUsername(token);

            } catch (Exception e) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                response.getWriter()
                        .write("Invalid JWT Token");

                return;
            }
        }

        // Authenticate User
        if (username != null
                && SecurityContextHolder.getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    userService.loadUserByUsername(username);

            // Validate Token
            if (jwtUtil.validateToken(token)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}