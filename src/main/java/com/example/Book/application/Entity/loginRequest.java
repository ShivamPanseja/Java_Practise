package com.example.book.application.entity;

// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;

// import jakarta.persistence.Entity;

// import jakarta.persistence.Id;
// import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class loginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
