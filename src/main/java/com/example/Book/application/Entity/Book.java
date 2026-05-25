package com.example.book.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// import org.springframework.data.annotation.CreationTimestamp;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Book {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String author;
    private String genre;
    @UpdateTimestamp
    private LocalDateTime updateTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String imageUrl;
}

