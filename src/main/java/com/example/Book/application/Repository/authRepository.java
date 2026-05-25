package com.example.book.application.repository;

import com.example.book.application.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface authRepository extends JpaRepository<Auth, Long> {
    Optional <Auth> findByEmail(String email);
    boolean existsByEmail(String email);

}
