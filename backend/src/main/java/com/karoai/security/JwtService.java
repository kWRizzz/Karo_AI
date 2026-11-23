package com.karoai.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

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

    // generate tokens

    public String generateAccessToken(Long userId){
        Date now= new Date();

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(new Date(now.getTime() + accessExpiration))
            .signWith(secretKey)
            .compact();
    }

    // getting  the user using tokens

    public Long extractUserId(String token){
        Claims claims= Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    // validating the token 

    public boolean isTokenValid(String token){
        try {
            extractUserId(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public long getAccessExpiration() {
        return accessExpiration;
    }
}
