package com.example.bookMgment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity class representing a Book in the database.
 * Mapped to the "books" table.
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * Primary Key.
     * Auto-generated ID using Identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the book. Cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Author of the book. Cannot be null.
     */
    @Column(nullable = false)
    private String author;

    /**
     * Unique ISBN for the book.
     */
    @Column(unique = true)
    private String isbn;

    /**
     * Year the book was published.
     */
    @Column(name = "publication_year")
    private Integer publicationYear;

    /**
     * Publisher name.
     */
    private String publisher;

    /**
     * Price of the book. Cannot be null.
     */
    @Column(nullable = false)
    private Double price;

    /**
     * Total number of pages in the book.
     */
    @Column(name = "total_pages")
    private Integer totalPages;

    /**
     * Description of the book content.
     * Length limit set to 1000 characters.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Timestamp when the record was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the record was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Lifecycle callback method.
     * Sets the createdAt timestamp before persisting a new entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Lifecycle callback method.
     * Updates the updatedAt timestamp before updating an existing entity.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
