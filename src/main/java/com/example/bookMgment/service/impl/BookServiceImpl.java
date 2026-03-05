package com.example.bookMgment.service.impl;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookmanagement.dto.BookResponse;
import com.example.bookMgment.entity.Book;
import com.example.bookMgment.exception.DuplicateIsbnException;
import com.example.bookMgment.exception.InvalidBookDataException;
import com.example.bookMgment.exception.ResourceNotFoundException;
import com.example.bookMgment.repository.BookRepository;
import com.example.bookMgment.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SERVICE IMPLEMENTATION: Contains actual business logic.
 *
 * ANNOTATIONS EXPLAINED:
 * @Service - Marks as Spring Service bean
 * @Transactional - Manages database transactions
 * @Slf4j - Adds logging capability
 * @RequiredArgsConstructor - Creates constructor for final fields (Dependency Injection)
 */
@Service
@Transactional  // All methods run within a transaction
@RequiredArgsConstructor  // Constructor injection for dependencies
@Slf4j  // Provides 'log' object for logging
public class BookServiceImpl implements BookService {

    /**
     * DEPENDENCY INJECTION: Constructor injection (via @RequiredArgsConstructor)
     * Why final? Ensures immutability and thread-safety
     */
    private final BookRepository bookRepository;

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        log.info("Creating new book with ISBN: {}", bookRequest.getIsbn());

        // BUSINESS VALIDATION: Check for duplicate ISBN
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            log.error("Book with ISBN {} already exists", bookRequest.getIsbn());
            throw new DuplicateIsbnException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        // BUSINESS RULE: Validate publication year
        if (bookRequest.getPublicationYear() != null &&
                bookRequest.getPublicationYear() > java.time.Year.now().getValue()) {
            throw new InvalidBookDataException("Publication year cannot be in the future");
        }

        // Map DTO to Entity
        Book book = mapToEntity(bookRequest);

        // Save to database
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());

        // Map Entity to Response DTO and return
        return mapToResponse(savedBook);
    }

    @Override
    public BookResponse getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);

        // Find book or throw exception
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found with ID: {}", id);
                    return new ResourceNotFoundException("Book not found with id: " + id);
                });

        return mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        log.info("Fetching all books");

        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * GET BOOKS WITH PAGINATION
     * Alternative method for large datasets
     */
    public Page<BookResponse> getAllBooksPaginated(int page, int size, String sortBy) {
        log.info("Fetching books - Page: {}, Size: {}, SortBy: {}", page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        return bookRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        log.info("Updating book with ID: {}", id);

        // Check if book exists
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check ISBN uniqueness if it's being changed
        if (!existingBook.getIsbn().equals(bookRequest.getIsbn()) &&
                bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new DuplicateIsbnException("Book with ISBN " + bookRequest.getIsbn() + " already exists");
        }

        // Update fields
        updateEntity(existingBook, bookRequest);

        // Save updated book
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book updated successfully with ID: {}", updatedBook.getId());

        return mapToResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);

        // Check if book exists before deleting
        if (!bookRepository.existsById(id)) {
            log.error("Cannot delete - Book not found with ID: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
        log.info("Book deleted successfully with ID: {}", id);
    }

    @Override
    public List<BookResponse> getBooksByAuthor(String author) {
        log.info("Fetching books by author: {}", author);

        return bookRepository.findByAuthor(author)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        log.info("Searching books with keyword: {}", keyword);

        return bookRepository.searchBooks(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching books with price between {} and {}", minPrice, maxPrice);

        return bookRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBookAvailable(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

    @Override
    public Long countBooksByAuthor(String author) {
        return (long) bookRepository.findByAuthor(author).size();
    }

    @Override
    public List<BookResponse> getRecentBooks() {
        return bookRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(10)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * PRIVATE HELPER METHODS
     * Used for mapping between Entity and DTO
     */

    /**
     * Maps BookRequest DTO to Book Entity
     */
    private Book mapToEntity(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setPublisher(request.getPublisher());
        book.setPrice(request.getPrice());
        book.setTotalPages(request.getTotalPages());
        book.setDescription(request.getDescription());
        // createdAt and updatedAt are set automatically via @PrePersist/@PreUpdate
        return book;
    }

    /**
     * Updates existing Entity with Request DTO data
     */
    private void updateEntity(Book book, BookRequest request) {
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setPublisher(request.getPublisher());
        book.setPrice(request.getPrice());
        book.setTotalPages(request.getTotalPages());
        book.setDescription(request.getDescription());
        // updatedAt will be updated automatically via @PreUpdate
    }

    /**
     * Maps Book Entity to BookResponse DTO
     */
    private BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .publisher(book.getPublisher())
                .price(book.getPrice())
                .totalPages(book.getTotalPages())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}