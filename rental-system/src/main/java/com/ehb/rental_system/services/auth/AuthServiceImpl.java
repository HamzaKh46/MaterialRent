package com.ehb.rental_system.services.auth;


import com.ehb.rental_system.dto.SignupRequest;
import com.ehb.rental_system.dto.UserDto;
import com.ehb.rental_system.entity.User;
import com.ehb.rental_system.enums.UserRole;
import com.ehb.rental_system.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);

        if(adminAccount.isEmpty()){
            User user = new User();
            user.setEmail("admin@ehb.be");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created");
        }else{
            System.out.println("Admin account already created");

        }
    }

    public UserDto createUser(SignupRequest signupRequest){

        if (userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User with email already exists");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.STUDENT);

        User savedUser = userRepository.save(user);
        return savedUser.getUserDto();
    }
}
