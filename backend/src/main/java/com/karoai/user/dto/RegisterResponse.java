package com.karoai.user.dto;

import java.time.LocalDateTime;

public record RegisterResponse(

    Long id,
    String name,
    String email,
    String timezone,
    LocalDateTime createdAt
) {
    

}
