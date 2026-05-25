package com.example.book.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.book.application.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.example.book.application.entity.Book;

@Service

public class BookService {
    
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        LocalDateTime.now();
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book, String token) {
        return bookRepository.save(book);
        // Logic to add the book to the database or perform any necessary operations
    }

    public Book getBoookById(Integer id, String token) {
        return bookRepository.findById(id).orElse(null);
    }

    public java.util.List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Integer id, Book book, String token) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setUpdateTime(LocalDateTime.now());
            return bookRepository.save(existingBook);
        }).orElse(null);
    }


    public Book deleteBook(Integer id, String token) {
        return bookRepository.findById(id).map(existingBook -> {
            bookRepository.delete(existingBook);
            return existingBook;
        }).orElse(null);
    }
}   


