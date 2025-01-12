package com.ehb.rental_system.controller.auth;


import com.ehb.rental_system.dto.AuthenticationRequest;
import com.ehb.rental_system.dto.AuthenticationResponse;
import com.ehb.rental_system.dto.SignupRequest;
import com.ehb.rental_system.dto.UserDto;
import com.ehb.rental_system.repository.UserRepository;
import com.ehb.rental_system.services.auth.AuthService;
import com.ehb.rental_system.services.jwt.UserService;
import com.ehb.rental_system.util.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.ehb.rental_system.entity.User;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;




    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            UserDto createdUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }catch (EntityExistsException e) {
            return new ResponseEntity<>("User already exist.", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e) {
            return new ResponseEntity<>("User not created.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/login")
    public AuthenticationResponse createAuthentificationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password.");
        }


        // Load the user details based on the email provided in the authentication request
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());

        // Find the user by email from the database using the user repository
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) { // If the user exists, set the JWT token, user role, and user ID in the response

            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }

        return authenticationResponse;


    }



}
