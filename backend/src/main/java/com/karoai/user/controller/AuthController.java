package com.karoai.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karoai.user.dto.AuthResponse;
import com.karoai.user.dto.LoginRequest;
import com.karoai.user.dto.LogoutRequest;
import com.karoai.user.dto.RefreshRequest;
import com.karoai.user.dto.RegisterRequest;
import com.karoai.user.dto.RegisterResponse;
import com.karoai.user.service.AuthService;
import com.karoai.user.service.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(
        UserService userService,
        AuthService authService
    ){
        this.userService=userService;
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
        @Valid @RequestBody RefreshRequest request
    ) {
        return ResponseEntity.ok(
            authService.refresh(request.refreshToken())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @Valid @RequestBody LogoutRequest request
    ) {
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
