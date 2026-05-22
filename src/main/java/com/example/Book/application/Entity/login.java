package com.example.Book.application.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
// import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;
    private String password;

}
