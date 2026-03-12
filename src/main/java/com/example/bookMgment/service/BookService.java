package com.example.bookMgment.service;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookMgment.dto.BookResponse;
import java.util.List;

/**
 * Service interface for Book management operations.
 * Defines the contract for business logic related to books.
 */
public interface BookService {

    /**
     * Creates a new book entry.
     * @param request The book creation request data.
     * @return The created book response.
     */
    BookResponse createBook(BookRequest request);

    /**
     * Retrieves a book by its unique ID.
     * @param id The ID of the book.
     * @return The book response found.
     */
    BookResponse getBookById(Long id);

    /**
     * Retrieves all books available in the system.
     * @return A list of all book responses.
     */
    List<BookResponse> getAllBooks();

    /**
     * Updates an existing book.
     * @param id The ID of the book to update.
     * @param request The updated book data.
     * @return The updated book response.
     */
    BookResponse updateBook(Long id, BookRequest request);

    /**
     * Deletes a book from the system.
     * @param id The ID of the book to delete.
     */
    void deleteBook(Long id);
}
