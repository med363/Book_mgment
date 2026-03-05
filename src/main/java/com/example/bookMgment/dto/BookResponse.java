package com.example.bookmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO FOR RESPONSES: Used when sending book data back to client.
 *
 * WHY BUILDER PATTERN?
 * - Provides flexible object creation
 * - Makes code more readable
 * - Immutable objects (thread-safe)
 */
@Data
@Builder  // Enables Builder pattern: BookResponse.builder().id(1L).title("...").build()
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;  // We expose ID in response so client can reference the book

    private String title;

    private String author;

    private String isbn;

    private Integer publicationYear;

    private String publisher;

    private Double price;

    private Integer totalPages;

    private String description;

    private LocalDateTime createdAt;  // Audit fields for transparency

    private LocalDateTime updatedAt;

    /**
     * Note: We don't include sensitive data here
     * If we had fields like "internalNotes", we would NOT expose them
     */
}