package com.karoai.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karoai.user.dto.RegisterRequest;
import com.karoai.user.dto.RegisterResponse;
import com.karoai.user.service.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/v1/auth")
public class AuthController {
    private final UserService userService;


    public AuthController(
        UserService userService
    ){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
       @Valid @RequestBody RegisterRequest request
    ){
        RegisterResponse response=userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
