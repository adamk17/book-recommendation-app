package com.example.recommendationservice.service;

import com.example.recommendationservice.dto.RecommendationDto;
import com.example.recommendationservice.dto.ReviewDto;
import com.example.recommendationservice.service.client.BookClient;
import com.example.recommendationservice.service.client.ReviewClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final BookClient bookClient;
    private final ReviewClient reviewClient;

    public RecommendationService(BookClient bookClient, ReviewClient reviewClient) {
        this.bookClient = bookClient;
        this.reviewClient = reviewClient;
    }

    public List<RecommendationDto> getRecommendationsForUser(Long userId) {
        List<ReviewDto> userReviews = reviewClient.getReviewsByUser(userId);
        Set<Long> reviewedBookIds = userReviews.stream()
                .map(ReviewDto::bookId)
                .collect(Collectors.toSet());

        return bookClient.getAllBooks().stream()
                .filter(book -> !reviewedBookIds.contains(book.id()))
                .map(book -> new RecommendationDto(book.id(), book.title(), book.author()))
                .collect(Collectors.toList());
    }
}
