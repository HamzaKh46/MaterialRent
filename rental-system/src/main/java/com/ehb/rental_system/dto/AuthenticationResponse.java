package com.ehb.rental_system.dto;

import com.ehb.rental_system.enums.UserRole;
import lombok.Data;


// AuthenticationResponse class represents the data that the backend returns to the client after successful authentication.
// It contains the JWT token, the user ID, and the user's role, which the client needs to manage the user's session and authorization.
@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;

    private UserRole userRole;
}
