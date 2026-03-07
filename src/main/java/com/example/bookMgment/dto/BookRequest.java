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

    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "Invalid ISBN format")
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