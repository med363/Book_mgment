package com.example.bookMgment.repository;

import com.example.bookMgment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Book entity.
 * Extends JpaRepository to provide standard CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Checks if a book exists with the given ISBN.
     * Derived query method.
     *
     * @param isbn The ISBN to check.
     * @return true if a book with the ISBN exists, false otherwise.
     */
    boolean existsByIsbn(String isbn);
}
