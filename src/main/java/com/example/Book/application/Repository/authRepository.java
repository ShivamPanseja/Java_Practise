package com.example.Book.application.Repository;

import com.example.Book.application.Entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface authRepository extends JpaRepository<Auth, Long> {
    Optional <Auth> findByEmail(String email);
    boolean existsByEmail(String email);

}
