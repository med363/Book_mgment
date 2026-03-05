package com.example.bookMgment.controller;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookmanagement.dto.BookResponse;
import com.example.bookMgment.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * CONTROLLER LAYER: Handles HTTP requests/responses.
 *
 * WHY CONTROLLER?
 * - Entry point for all HTTP requests
 * - Request validation
 * - Response formatting
 * - HTTP status code management
 * - API endpoint definition
 */
@RestController  // Combines @Controller and @ResponseBody
@RequestMapping("/api/v1/books")  // Base URL for all endpoints in this controller
@RequiredArgsConstructor  // Constructor injection
@Slf4j  // Logging
@CrossOrigin(origins = "*")  // Allow requests from different origins (CORS)
public class BookController {

    private final BookService bookService;

    /**
     * POST /api/v1/books - Create a new book
     *
     * @Valid - Triggers validation on BookRequest
     * @RequestBody - Binds HTTP body to method parameter
     * ResponseEntity - Gives full control over HTTP response
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info("POST /api/v1/books - Creating new book");
        BookResponse createdBook = bookService.createBook(bookRequest);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);  // 201 Created
    }

    /**
     * GET /api/v1/books/{id} - Get book by ID
     *
     * @PathVariable - Binds path variable {id} to method parameter
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("GET /api/v1/books/{} - Fetching book", id);
        BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);  // 200 OK
    }

    /**
     * GET /api/v1/books - Get all books
     * Alternative endpoints with filtering
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String search) {

        log.info("GET /api/v1/books - Fetching books with filters - author: {}, priceRange: {}-{}, search: {}",
                author, minPrice, maxPrice, search);

        // Apply filters based on query parameters
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(bookService.searchBooks(search));
        } else if (author != null && !author.isEmpty()) {
            return ResponseEntity.ok(bookService.getBooksByAuthor(author));
        } else if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(bookService.getBooksByPriceRange(minPrice, maxPrice));
        } else {
            return ResponseEntity.ok(bookService.getAllBooks());
        }
    }

    /**
     * GET /api/v1/books/paginated - Get books with pagination
     * Shows how to handle pagination parameters
     */
    @GetMapping("/paginated")
    public ResponseEntity<Page<BookResponse>> getAllBooksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {

        log.info("GET /api/v1/books/paginated - Page: {}, Size: {}, SortBy: {}", page, size, sortBy);
        // Note: You'd need to add this method to your service
        // return ResponseEntity.ok(bookService.getAllBooksPaginated(page, size, sortBy));
        return ResponseEntity.ok().build();  // Placeholder
    }

    /**
     * GET /api/v1/books/recent - Get recent books
     */
    @GetMapping("/recent")
    public ResponseEntity<List<BookResponse>> getRecentBooks() {
        log.info("GET /api/v1/books/recent - Fetching recent books");
        return ResponseEntity.ok(bookService.getRecentBooks());
    }

    /**
     * GET /api/v1/books/isbn/{isbn} - Get book by ISBN
     * Alternative lookup method
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        log.info("GET /api/v1/books/isbn/{} - Fetching book by ISBN", isbn);
        // Note: You'd need to add this method to your service
        // return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
        return ResponseEntity.ok().build();  // Placeholder
    }

    /**
     * GET /api/v1/books/count/author/{author} - Count books by author
     */
    @GetMapping("/count/author/{author}")
    public ResponseEntity<Long> countBooksByAuthor(@PathVariable String author) {
        log.info("GET /api/v1/books/count/author/{} - Counting books", author);
        return ResponseEntity.ok(bookService.countBooksByAuthor(author));
    }

    /**
     * GET /api/v1/books/check/{isbn} - Check if book exists by ISBN
     */
    @GetMapping("/check/{isbn}")
    public ResponseEntity<Boolean> checkBookAvailability(@PathVariable String isbn) {
        log.info("GET /api/v1/books/check/{} - Checking availability", isbn);
        return ResponseEntity.ok(bookService.isBookAvailable(isbn));
    }

    /**
     * PUT /api/v1/books/{id} - Update entire book
     * Full update - all fields required
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest bookRequest) {

        log.info("PUT /api/v1/books/{} - Updating book", id);
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * PATCH /api/v1/books/{id} - Partial update
     * For updating specific fields only
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> partialUpdateBook(
            @PathVariable Long id,
            @RequestBody BookRequest bookRequest) {

        log.info("PATCH /api/v1/books/{} - Partially updating book", id);
        // Note: You'd need to handle partial updates in service
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * DELETE /api/v1/books/{id} - Delete book
     * Returns 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        log.info("DELETE /api/v1/books/{} - Deleting book", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    /**
     * HEAD /api/v1/books/{id} - Check if book exists
     * Returns headers only, no body
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkBookExists(@PathVariable Long id) {
        log.info("HEAD /api/v1/books/{} - Checking existence", id);
        try {
            bookService.getBookById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * OPTIONS /api/v1/books - Get allowed HTTP methods
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        log.info("OPTIONS /api/v1/books - Getting allowed methods");
        return ResponseEntity
                .ok()
                .header("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS")
                .build();
    }
}