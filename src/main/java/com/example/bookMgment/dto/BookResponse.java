package com.example.bookMgment.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String publisher;
    private Double price;
    private Integer totalPages;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}