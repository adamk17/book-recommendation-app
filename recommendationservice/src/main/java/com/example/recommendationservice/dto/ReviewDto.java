package com.example.recommendationservice.dto;

public record ReviewDto(Long id, Long bookId, Long userId, Integer rating, String comment) {}
