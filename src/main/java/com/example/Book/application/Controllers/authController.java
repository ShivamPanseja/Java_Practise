package com.example.Book.application.Controllers;
import com.example.Book.application.Entity.Auth;
import com.example.Book.application.Entity.login;

import com.example.Book.application.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Book.application.dtos.LoginRequest;

import com.example.Book.application.dtos.RegisterRequest;

@RestController
@RequestMapping("/api/auth")

public class authController {
    private final authService authService;

    @Autowired
    public authController(authService authService) {
        this.authService = authService;

    }
    @PostMapping("/register")
    public ResponseEntity<Auth> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        // this is the right way to do
        Auth auth = authService.registerUser(registerRequest);

        return ResponseEntity.ok(auth);
    }

     @PostMapping("/login")
    public ResponseEntity<login> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        // this is the right way to do
        login log = authService.loginUser(loginRequest);

        return ResponseEntity.ok(log);
    }

}