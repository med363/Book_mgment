package com.example.bookMgment.controller;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookMgment.dto.BookResponse;
import com.example.bookMgment.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing Book resources.
 * Exposes endpoints for creating, retrieving, updating, and deleting books.
 * Base URL: /v1/books
 */
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * Creates a new book.
     * 
     * @param request The book details from the request body. Validated via @Valid.
     * @return The created book response with HTTP 201 Created status.
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        log.info("POST /books - Creating new book");
        return new ResponseEntity<>(bookService.createBook(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves all books.
     * 
     * @return A list of all books with HTTP 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("GET /books - Retrieving all books");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Retrieves a specific book by its ID.
     * 
     * @param id The ID of the book to retrieve.
     * @return The requested book response with HTTP 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("GET /books/{} - Retrieving book by ID", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Updates an existing book.
     * 
     * @param id The ID of the book to update.
     * @param request The updated book details. Validated via @Valid.
     * @return The updated book response with HTTP 200 OK status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        log.info("PUT /books/{} - Updating book", id);
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    /**
     * Deletes a book by its ID.
     * 
     * @param id The ID of the book to delete.
     * @return HTTP 204 No Content status on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("DELETE /books/{} - Deleting book", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
