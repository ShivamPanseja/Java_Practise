package com.example.Book.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.Book.application.Repository.BookRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.example.Book.application.Entity.Book;

@Service

public class BookService {
    
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        LocalDateTime.now();
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
        // Logic to add the book to the database or perform any necessary operations
    }

    public Book getBoookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(Integer id, Book book) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setUpdateTime(LocalDateTime.now());
            return bookRepository.save(existingBook);
        }).orElse(null);
    }


    public Book deleteBook(Integer id) {
        return bookRepository.findById(id).map(existingBook -> {
            bookRepository.delete(existingBook);
            return existingBook;
        }).orElse(null);
    }
}   


