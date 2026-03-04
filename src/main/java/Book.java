import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * ENTITY CLASS: Represents the database table structure.
 * Entity classes are directly mapped to database tables.
 * They contain JPA annotations that define the database schema.
 *
 * WHY WE NEED THIS: This is the internal representation of our data that matches
 * the database structure. It should NEVER be exposed directly to the client.
 */
@Entity  // Marks this class as a JPA entity (will be mapped to a database table)
@Table(name = "books")  // Specifies the table name in the database
@Data  // Lombok annotation that generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Generates a no-args constructor (required by JPA)
@AllArgsConstructor  // Generates a constructor with all arguments
public class Book {

    @Id  // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Database will auto-generate the ID
    private Long id;

    @Column(nullable = false, length = 200)  // Maps to database column with NOT NULL constraint
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(unique = true, length = 20)  // UNIQUE constraint in database
    private String isbn;  // International Standard Book Number

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(length = 50)
    private String publisher;

    @Column(nullable = false)
    private Double price;

    @Column(name = "total_pages")
    private Integer totalPages;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;  // Timestamp when record was created

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Timestamp when record was last updated

    /**
     * JPA Lifecycle Callback Methods
     * These methods are automatically called by JPA at specific points in the entity's lifecycle
     */

    /**
     * @PrePersist - Executed before the entity is persisted (inserted) to database
     * Used to set audit fields automatically
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * @PreUpdate - Executed before the entity is updated in database
     * Updates the last modified timestamp
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * IMPORTANT: Notice that we don't have any business logic here.
     * Entities should only contain:
     * 1. Fields mapping to database columns
     * 2. JPA annotations
     * 3. Lifecycle callback methods
     * 4. Basic validation annotations
     *
     * Business logic belongs in the Service layer!
     */
}