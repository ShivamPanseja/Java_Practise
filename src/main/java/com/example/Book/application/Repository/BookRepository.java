package com.example.book.application.repository;

import com.example.book.application.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
