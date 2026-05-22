package com.example.Book.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Pass is required")
    private String password;

  
}