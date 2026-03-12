package com.example.bookMgment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating and updating books.
 * Contains validation constraints to ensure data integrity.
 * Used in @RequestBody of controller methods.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    /**
     * Book Title.
     * Must not be blank and length must be between 2 and 200 characters.
     */
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200)
    private String title;

    /**
     * Book Author.
     * Must not be blank and length must be between 2 and 100 characters.
     */
    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100)
    private String author;

    /**
     * International Standard Book Number (ISBN).
     * Must not be blank.
     * Validated using a simplified length check (10-20 chars) for flexibility.
     */
    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
    private String isbn;

    /**
     * Publication Year.
     * Must be a valid year between 1000 and 2026.
     * Business logic also checks that it is not in the future.
     */
    @Min(1000)
    @Max(2026)
    private Integer publicationYear;

    /**
     * Book Publisher. Optional field.
     */
    private String publisher;

    /**
     * Book Price.
     * Must be provided and must be a positive value (min 0.0).
     */
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10000.0")
    private Double price;

    /**
     * Total number of pages.
     * Must be at least 1 and at most 10000.
     */
    @Min(1)
    @Max(10000)
    private Integer totalPages;

    /**
     * Brief description of the book.
     * Max length 1000 characters.
     */
    @Size(max = 1000)
    private String description;
}
