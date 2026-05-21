package com.example.Book.application.Controllers;
import com.example.Book.application.Entity.Auth;
import com.example.Book.application.service.authService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class authController {
    private final authService authService;

    @Autowired
    public authController(authService authService) {
        this.authService = authService;

    }
    @PostMapping("/register")
    public ResponseEntity <Auth> registerUser(@RequestBody Auth auth) {
        auth = authService.registerUser(auth);
        return ResponseEntity.ok(auth);
    }

}
