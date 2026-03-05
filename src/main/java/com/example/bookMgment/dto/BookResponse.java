package com.example.bookMgment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.Pattern;

/**
 * DTO FOR REQUESTS: Used when client sends data to create/update a book.
 *
 * WHY DTO?
 * 1. Security: Never expose entity directly to client
 * 2. Validation: Can add validation annotations without affecting entity
 * 3. Flexibility: Can have different fields than entity
 * 4. Decoupling: Changes in entity don't affect API contract
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")  // Validation: Cannot be null or empty
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String author;

    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "Invalid ISBN format")
    private String isbn;

    @Min(value = 1000, message = "Publication year must be at least 1000")
    @Max(value = 2024, message = "Publication year cannot be in the future")
    private Integer publicationYear;

    private String publisher;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price cannot exceed 10000")
    private Double price;

    @Min(value = 1, message = "Book must have at least 1 page")
    @Max(value = 10000, message = "Book cannot have more than 10000 pages")
    private Integer totalPages;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
}