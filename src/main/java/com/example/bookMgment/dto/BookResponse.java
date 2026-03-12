package com.example.bookMgment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for returning book details.
 * Used as response payload in API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    /**
     * Unique identifier for the book.
     */
    private Long id;

    /**
     * Title of the book.
     */
    private String title;

    /**
     * Author of the book.
     */
    private String author;

    /**
     * International Standard Book Number (ISBN).
     */
    private String isbn;

    /**
     * Year of publication.
     */
    private Integer publicationYear;

    /**
     * Publisher name.
     */
    private String publisher;

    /**
     * Price of the book.
     */
    private Double price;

    /**
     * Total number of pages.
     */
    private Integer totalPages;

    /**
     * Brief description.
     */
    private String description;

    /**
     * Timestamp when the book record was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when the book record was last updated.
     */
    private LocalDateTime updatedAt;
}
