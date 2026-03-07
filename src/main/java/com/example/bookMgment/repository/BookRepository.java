package com.example.bookMgment.repository;

import com.example.bookMgment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
}