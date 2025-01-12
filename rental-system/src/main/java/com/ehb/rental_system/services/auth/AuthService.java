package com.ehb.rental_system.services.auth;

import com.ehb.rental_system.dto.SignupRequest;
import com.ehb.rental_system.dto.UserDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
