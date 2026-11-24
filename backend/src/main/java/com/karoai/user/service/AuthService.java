package com.karoai.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karoai.security.JwtService;
import com.karoai.security.RefreshTokenService;
import com.karoai.user.dto.AuthResponse;
import com.karoai.user.dto.LoginRequest;
import com.karoai.user.entity.User;
import com.karoai.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
        PasswordEncoder passwordEncoder,
        UserRepository userRepository,
        JwtService jwtService,
        RefreshTokenService refreshTokenService
    ){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
        this.jwtService=jwtService;
        this.refreshTokenService=refreshTokenService;
    }

    public AuthResponse login(LoginRequest request){
        String email= request.email().trim().toLowerCase();
        User user=userRepository.findByEmail(email)
            .orElseThrow(()-> new IllegalArgumentException("ghee over"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new IllegalArgumentException("Pass and email galat hai ");
        }

        return generateResponse(user,refreshTokenService.createToken(user));
    }

    @Transactional 
    public AuthResponse refresh(String rawToken){
        RefreshTokenService.RotationResult result= refreshTokenService.rotateToken(rawToken);

        return generateResponse(
            result.user(),
            result.refreshToken()
        );
    }

    public void logout(String refreshToken){
        refreshTokenService.revokeToken(refreshToken);
    }

    public AuthResponse generateResponse(
        User user,
        String refreshToken
    ){
        return new AuthResponse(
            "Bearer",
            jwtService.generateAccessToken(user.getId()),
            jwtService.getAccessExpiration(),
            refreshToken
        );
    }
}
