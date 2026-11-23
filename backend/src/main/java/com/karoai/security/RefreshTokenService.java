package com.karoai.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;

import com.karoai.user.entity.RefreshToken;
import com.karoai.user.entity.User;
import com.karoai.user.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;

public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final long expiration;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenRepository repository,
            @Value("${app.jwt.refresh-expiration}") long expiration) {
        this.repository = repository;
        this.expiration = expiration;
    }

    @Transactional
    public String createToken(User user) {
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setTokenHash(hashToken(rawToken));
        token.setExpiresAt(
                LocalDateTime.now().plusNanos(expiration * 1_000_000));
        repository.save(token);
        return rawToken;
    }

    public record RotationResult(User user, String refreshToken) {}
    @Transactional
    public RotationResult rotateToken(String rawToken) {
        RefreshToken oldToken = repository.findByTokenHashAndRevokedFalse(hashToken(rawToken))
                .orElseThrow(() -> new IllegalArgumentException("Ghee Khatam"));

        if (oldToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            oldToken.setRevoked(true);
            throw new IllegalArgumentException("Refresh token expired");
        }

        oldToken.setRevoked(true);
        User user= oldToken.getUser();
        String  newToken= createToken(user);

        createToken(user);
        return new RotationResult(user, newToken);
    }

 


    @Transactional 
    public void revokeToken(String rawToken){
        repository.findByTokenHashAndRevokedFalse(hashToken(rawToken))
            .ifPresent(token->token.setRevoked(true));
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8));
            return java.util.HexFormat.of().formatHex(hash);
        } catch (Exception ex) {
            throw new IllegalStateException("Token hashing failed", ex);
        }
    }

}
