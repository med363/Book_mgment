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

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse createBook(BookRequest request) {
        log.info("Creating book with ISBN: {}", request.getIsbn());

        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException("ISBN already exists: " + request.getIsbn());
        }

        if (request.getPublicationYear() != null &&
                request.getPublicationYear() > Year.now().getValue()) {
            throw new InvalidBookDataException("Publication year cannot be in the future");
        }

        Book book = mapToEntity(request);
        Book saved = bookRepository.save(book);
        return mapToResponse(saved);
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        if (!existing.getIsbn().equals(request.getIsbn()) &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException("ISBN already exists: " + request.getIsbn());
        }

        updateEntity(existing, request);
        Book updated = bookRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        log.info("Deleted book with id: {}", id);
    }

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