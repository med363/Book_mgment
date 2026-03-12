package com.example.bookMgment.service.impl;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookMgment.dto.BookResponse;
import com.example.bookMgment.entity.Book;
import com.example.bookMgment.exception.DuplicateIsbnException;
import com.example.bookMgment.exception.InvalidBookDataException;
import com.example.bookMgment.exception.ResourceNotFoundException;
import com.example.bookMgment.repository.BookRepository;
import com.example.bookMgment.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BookService interface.
 * Contains business logic for book management operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Creates a new book after validation.
     * Checks for duplicate ISBN and invalid publication year.
     *
     * @param request The book creation request.
     * @return The created book response.
     * @throws DuplicateIsbnException if the ISBN already exists.
     * @throws InvalidBookDataException if the data is invalid (e.g., future year).
     */
    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("Creating book with ISBN: {}", request.getIsbn());

        // Check if ISBN already exists
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException("ISBN already exists: " + request.getIsbn());
        }

        // Validate publication year
        if (request.getPublicationYear() != null &&
                request.getPublicationYear() > Year.now().getValue()) {
            throw new InvalidBookDataException("Publication year cannot be in the future");
        }

        Book book = mapToEntity(request);
        Book saved = bookRepository.save(book);
        return mapToResponse(saved);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book.
     * @return The book response.
     * @throws ResourceNotFoundException if the book is not found.
     */
    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    /**
     * Retrieves all books.
     *
     * @return A list of all book responses.
     */
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing book.
     * Checks for ISBN uniqueness if the ISBN is being changed.
     *
     * @param id The ID of the book to update.
     * @param request The update request.
     * @return The updated book response.
     * @throws ResourceNotFoundException if the book is not found.
     * @throws DuplicateIsbnException if the new ISBN conflicts with another book.
     */
    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check if the ISBN is being changed and if the new ISBN already exists
        if (!existing.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException("ISBN already exists: " + request.getIsbn());
        }

        updateEntity(existing, request);
        Book updated = bookRepository.save(existing);
        return mapToResponse(updated);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @throws ResourceNotFoundException if the book is not found.
     */
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Deleted book with id: {}", id);
    }

    /**
     * Maps a BookRequest DTO to a Book entity.
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
        return book;
    }

    /**
     * Updates a Book entity with values from a BookRequest DTO.
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
    }

    /**
     * Maps a Book entity to a BookResponse DTO.
     */
    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getPrice(),
                book.getTotalPages(),
                book.getDescription(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
