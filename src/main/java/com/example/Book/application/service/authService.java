package com.example.Book.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Book.application.Repository.authRepository;
import com.example.Book.application.Entity.Auth;

@Service
public class authService {

    private final authRepository authRepository;
    @Autowired
    public authService(authRepository authRepository) {
        this.authRepository = authRepository;

    }

    public Auth registerUser(Auth auth) {
        return authRepository.save(auth);
    }

    
}
