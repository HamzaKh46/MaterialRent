package com.ehb.rental_system.configs;


import com.ehb.rental_system.services.jwt.UserService;
import com.ehb.rental_system.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header and validate its format.
        // If the header is missing or doesn't start with "Bearer ", skip authentication and proceed.
        final String authnHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authnHeader) || !StringUtils.startsWith(authnHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header and retrieve the user email.
        final String jwt = authnHeader.substring(7); // Remove the "Bearer " prefix.
        final String userEmail = jwtUtil.extractUserName(jwt); // Decode the token to get the user's email.

        // Authenticate the user if not already authenticated.
        // Load user details and verify the JWT token's validity.
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                // Create a security context with the user's authentication details.
                // A SecurityContext stores the authenticated user's identity and roles, enabling the application to manage authorization.
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        // Continue with the filter chain, regardless of authentication status.
        filterChain.doFilter(request, response);
    }
}
