package com.example.Book.application.Repository;

import com.example.Book.application.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
