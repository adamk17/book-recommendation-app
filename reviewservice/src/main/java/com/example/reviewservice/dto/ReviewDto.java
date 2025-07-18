package com.example.reviewservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewDto(
        @NotNull
        Long bookId,
        @NotNull
        Long userId,
        @Min(1) @Max(5)
        Integer rating,
        @Size(min = 1, max = 1000)
        String comment
) {}
