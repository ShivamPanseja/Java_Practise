package com.example.book.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.book.application.service.BookService;
import com.example.book.application.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;

// import jakarta.persistence.PostPersist;              

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/addbook")
    public ResponseEntity <Book> addBook(@ModelAttribute Book book,

        @RequestParam("file") MultipartFile file,

        @RequestHeader("Authorization") String token) {
        System.out.println("Reached at controller");
        Book savedBook = bookService.addBook(book, token);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/getbook/{id}")
    public ResponseEntity <Book> getBookById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        Book book = bookService.getBoookById(id, token);
        return ResponseEntity.ok(book);

    }

    @GetMapping("/books")
    public ResponseEntity<java.util.List<Book>> getAllBooks(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/updatebook/{id}")
    public ResponseEntity <Book> updateBook(@PathVariable Integer id, @RequestBody Book book, @RequestHeader("Authorization") String token) {
        Book updateBook = bookService.updateBook(id, book, token);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity <String> deleteBook(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        Book deleteBook = bookService.deleteBook(id, token);
        return ResponseEntity.ok("Book deleted successfully");
    }
}


