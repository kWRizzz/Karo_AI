package com.karoai.security;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service 
public class JwtService {
    private final SecretKey secretKey;
    private final long accessExpiration;

    public JwtService(
        @org.springframework.beans.factory.annotation.Value ("${app.jwt.secret}")String secret,
        @org.springframework.beans.factory.annotation.Value ("${app.jwt.access-expiration}") long accessExpiration
    ){
        byte[] keyBytes=Base64.getDecoder().decode(secret);

        if(keyBytes.length <32){
            throw new IllegalArgumentException(
                "JWT secret must contain atleast 32 bit"
            );
        }

        this.secretKey=new SecretKeySpec(keyBytes, "HmacSHA256");
        this.accessExpiration=accessExpiration;
    }
}
