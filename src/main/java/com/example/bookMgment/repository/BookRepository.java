package com.example.bookMgment.repository;

import com.example.bookMgment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * REPOSITORY LAYER: Handles database operations.
 *
 * WHY EXTEND JpaRepository?
 * - Provides CRUD methods out of the box (save, findById, findAll, deleteById)
 * - Implements Pagination and Sorting
 * - Allows custom query methods
 * - Manages transaction boundaries
 */
@Repository  // Indicates that this is a Spring Data repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Derived Query Methods - Spring Data JPA automatically implements these
     * based on method name convention
     */

    // Finds books by exact author name
    List<Book> findByAuthor(String author);

    // Finds books where title contains the given string (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Finds books by publication year
    List<Book> findByPublicationYear(Integer year);

    // Finds books by author and publication year
    List<Book> findByAuthorAndPublicationYear(String author, Integer year);

    // Checks if a book exists with given ISBN
    boolean existsByIsbn(String isbn);

    // Finds books with price less than specified amount
    List<Book> findByPriceLessThan(Double price);

    // Finds books with price between min and max
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Custom JPQL Query - More complex queries using Java Persistence Query Language
     *
     * @Param annotation maps method parameter to query parameter
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(@Param("keyword") String keyword);

    /**
     * Native SQL Query - Uses raw SQL (database-specific)
     * Use only when JPQL can't achieve what you need
     */
    @Query(value = "SELECT * FROM books WHERE price > :price AND publication_year > :year",
            nativeQuery = true)
    List<Book> findExpensiveRecentBooks(@Param("price") Double price, @Param("year") Integer year);

    /**
     * Find with sorting - Can be combined with Pageable for pagination
     */
    List<Book> findAllByOrderByCreatedAtDesc();

    /**
     * Optional return type - Forces client to handle absence
     * Better than returning null and causing NullPointerException
     */
    Optional<Book> findByIsbn(String isbn);
}