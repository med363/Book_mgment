package com.example.bookMgment.service;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookmanagement.dto.BookResponse;
import java.util.List;

/**
 * SERVICE INTERFACE: Defines the contract for business operations.
 *
 * WHY INTERFACE?
 * 1. Loose coupling between layers
 * 2. Easy to switch implementations
 * 3. Better for testing (mocking)
 * 4. Clearly defines what the service does
 */
public interface BookService {

    /**
     * CRUD Operations
     */
    BookResponse createBook(BookRequest bookRequest);

    BookResponse getBookById(Long id);

    List<BookResponse> getAllBooks();

    BookResponse updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    /**
     * Additional Business Operations
     */
    List<BookResponse> getBooksByAuthor(String author);

    List<BookResponse> searchBooks(String keyword);

    List<BookResponse> getBooksByPriceRange(Double minPrice, Double maxPrice);

    boolean isBookAvailable(String isbn);

    Long countBooksByAuthor(String author);

    List<BookResponse> getRecentBooks();  // Last 10 added books
}