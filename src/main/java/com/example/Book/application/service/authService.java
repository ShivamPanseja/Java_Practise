package com.example.Book.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Book.application.Repository.authRepository;
import com.example.Book.application.Entity.Auth;
import com.example.Book.application.Entity.login;
import com.example.Book.application.dtos.RegisterRequest;
import com.example.Book.application.dtos.LoginRequest;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
public class authService {

    private final authRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public authService(authRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Auth registerUser(RegisterRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Auth auth = new Auth();
        // this is the right way to do
        auth.setEmail(request.getEmail());
        auth.setPassword(passwordEncoder.encode(request.getPassword()));
        // Provide placeholders for required fields; consider extending DTO if needed
        auth.setFirstName(request.getFirstName() != null ? request.getFirstName() : ""); // Placeholder, adjust as needed
        auth.setLastName(request.getLastName() != null ? request.getLastName() : ""); // Placeholder, adjust as needed

        return authRepository.save(auth);
    }

    // login method
    public login loginUser(LoginRequest request) {
        Optional<Auth> authOptional = authRepository.findByEmail(request.getEmail());
        if (!authOptional.isPresent()) {
            throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Invalid email or password"
);
        }
        Auth auth = authOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), auth.getPassword())) {
            throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Invalid email or password"
);
        }
        return new login(request.getEmail(), request.getPassword());
    }
}
