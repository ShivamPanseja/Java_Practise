package com.example.book.application.controllers;

import com.example.book.application.entity.Auth;

import com.example.book.application.entity.loginRequest;
import com.example.book.application.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.book.application.dtos.LoginResponse;
import com.example.book.application.dtos.RegisterRequest;

@RestController
@RequestMapping("/api/auth")

public class authController {
    private final authService authService;

    @Autowired
    public authController(authService authService) {
        this.authService = authService;

    }

    @PostMapping("/register")
    public ResponseEntity<Auth> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // this is the right way to do
        Auth auth = authService.registerUser(registerRequest);

        return ResponseEntity.ok(auth);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody loginRequest loginRequest) {
        // this is the right way to do
        LoginResponse log = authService.loginUser(loginRequest);

        return ResponseEntity.ok(log);
    }

}