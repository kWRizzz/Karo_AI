package com.karoai.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import lombok.Value;

@Service 
public class JwtService {
    private final SecretKey secretKey;
    private final long accessExpiration;

    public JwtService(
        @Value ("${app.jwt.secret}")String secret;
    )
}
