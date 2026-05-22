package com.example.Book.application.Controllers;

// import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import com.example.Book.application.service.BookService;
import com.example.Book.application.Entity.Book;
import java.util.List;

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
    public ResponseEntity <Book> addBook(@RequestBody Book book) {
        System.out.println("Reached at controller");
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/getbook/{id}")
    public ResponseEntity <Book> getBookById(@PathVariable Integer id) {
        Book book = bookService.getBoookById(id);
        return ResponseEntity.ok(book);

    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/updatebook/{id}")
    public ResponseEntity <Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        Book updateBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity <String> deleteBook(@PathVariable Integer id) {
        Book deleteBook = bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}


