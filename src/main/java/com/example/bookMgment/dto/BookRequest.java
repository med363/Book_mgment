package com.example.bookMgment.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100)
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
    private String isbn;

    @Min(1000)
    @Max(2026)
    private Integer publicationYear;

    private String publisher;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10000.0")
    private Double price;

    @Min(1)
    @Max(10000)
    private Integer totalPages;

    @Size(max = 1000)
    private String description;
}