package com.example.Book.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Pass is required")
    private String password;

    // @NotBlank(message = "First name is required")
    private String firstName;

    // @NotBlank(message = "Last name is required")    
    private String lastName;
}

