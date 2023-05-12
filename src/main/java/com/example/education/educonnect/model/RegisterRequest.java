package com.example.education.educonnect.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed {max} characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must not exceed {max} characters")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone must not exceed {max} characters")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between {min} and {max} characters")
    private String password;

}
