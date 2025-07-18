package com.example.reviewservice.service;

import com.example.reviewservice.service.client.BookClient;
import com.example.reviewservice.service.client.UserClient;
import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.exception.ReviewNotFoundException;
import com.example.reviewservice.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private BookClient bookClient;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldCreateReview() {
        ReviewDto dto = new ReviewDto(1L, 1L, 4, "Nice");
        Review review = Review.builder()
                .id(1L)
                .bookId(1L)
                .userId(1L)
                .rating(4)
                .comment("Nice")
                .createdAt(LocalDateTime.now())
                .build();

        when(reviewRepository.save(any())).thenReturn(review);

        Review result = reviewService.createReview(dto);

        assertThat(result.getId()).isEqualTo(1L);
        verify(userClient).getUserById(1L);
        verify(bookClient).getBookById(1L);
    }

    @Test
    void shouldReturnAllReviews() {
        List<Review> list = List.of(new Review());
        when(reviewRepository.findAll()).thenReturn(list);

        List<Review> result = reviewService.getAllReviews();

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldReturnReviewById() {
        Review review = new Review();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowWhenReviewNotFound() {
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewService.getReviewById(999L))
                .isInstanceOf(ReviewNotFoundException.class);
    }

    @Test
    void shouldUpdateReview() {
        Review existing = new Review(1L, 1L, 1L, 3, "Ok", LocalDateTime.now());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ReviewDto dto = new ReviewDto(1L, 1L, 5, "Updated");

        Review result = reviewService.updateReview(1L, dto);

        assertThat(result.getRating()).isEqualTo(5);
        assertThat(result.getComment()).isEqualTo("Updated");
    }

    @Test
    void shouldDeleteReview() {
        when(reviewRepository.existsById(1L)).thenReturn(true);

        reviewService.deleteReview(1L);

        verify(reviewRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingReview() {
        when(reviewRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> reviewService.deleteReview(999L))
                .isInstanceOf(ReviewNotFoundException.class);
    }
}
