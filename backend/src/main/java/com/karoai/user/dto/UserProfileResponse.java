package com.karoai.user.dto;

import java.time.LocalDateTime;

public record UserProfileResponse(
    Long id,
    String name,
    String email,
    String timezone,
    LocalDateTime createdAt
) {
}