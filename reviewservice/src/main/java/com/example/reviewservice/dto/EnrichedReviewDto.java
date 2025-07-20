package com.example.reviewservice.dto;

import java.time.LocalDateTime;

public record EnrichedReviewDto(
        Long id,
        Long bookId,
        String bookTitle,
        String bookAuthor,
        Long userId,
        String username,
        String email,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}
