package com.karoai.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karoai.user.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{

    Optional<RefreshToken> findByTokenHashAndRevokedFalse(
        String tokenHash
    );
    
}
