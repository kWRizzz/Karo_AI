package com.karoai.user.service;

import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karoai.user.dto.RegisterRequest;
import com.karoai.user.dto.RegisterResponse;
import com.karoai.user.entity.User;
import com.karoai.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class UserService {
    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional 
    public RegisterResponse register(RegisterRequest request){
        String email= request.email().trim().toLowerCase(Locale.ROOT);
        
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("User already existed hai ");
        }

        User user= new User();

        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));

        User userSave= userRepository.save(user);

        return new RegisterResponse(
            userSave.getId(),
            userSave.getName(),
            userSave.getEmail(),
            userSave.getTimezone(),
            userSave.getCreatedAt()
        );

    }
}
