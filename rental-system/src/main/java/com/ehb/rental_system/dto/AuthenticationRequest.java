package com.ehb.rental_system.dto;


import lombok.Data;


// AuthenticationRequest class represents the data that the user sends to the backend during login.
// It contains the user's email and password, which are required to authenticate the user.
@Data
public class AuthenticationRequest {

    private String email;
    private String password;

}
