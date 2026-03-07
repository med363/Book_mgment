package com.example.bookMgment.service;

import com.example.bookMgment.dto.BookRequest;
import com.example.bookMgment.dto.BookResponse;
import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
}