package com.karoai.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @NotBlank (message = "Pls Fill The complete Form")
    @Size (max = 100,message = "Size cannot exceed 100 characters")
    String name,

    @NotBlank (message = "Email should be filled")
    @Email (message = "Innvalid email pls fill the correct one")
    @Size (max = 150)
    String email,

    @NotBlank (message = "Fill the passward")
    @Size (min = 6, max = 72, message = "min value should be 6 char and max val should be 72 ")
    String password
) {
   
}
