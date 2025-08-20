package com.example.recommendationservice.service;

import com.example.recommendationservice.dto.BookDto;
import com.example.recommendationservice.dto.RecommendationDto;
import com.example.recommendationservice.dto.ReviewDto;
import com.example.recommendationservice.service.client.BookClient;
import com.example.recommendationservice.service.client.ReviewClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private BookClient bookClient;

    @Mock
    private ReviewClient reviewClient;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void shouldReturnBooksNotReviewedByUser() {
        Long userId = 42L;

        List<ReviewDto> userReviews = List.of(
                new ReviewDto(10L, 1L, userId, 5, "super"),
                new ReviewDto(11L, 3L, userId, 3, "ok")
        );
        when(reviewClient.getReviewsByUser(userId)).thenReturn(userReviews);

        List<BookDto> allBooks = List.of(
                new BookDto(1L, "T1", "A1", null, null),
                new BookDto(2L, "T2", "A2", null, null),
                new BookDto(3L, "T3", "A3", null, null),
                new BookDto(4L, "T4", "A4", null, null)
        );
        when(bookClient.getAllBooks()).thenReturn(allBooks);

        List<RecommendationDto> result = recommendationService.getRecommendationsForUser(userId);

        assertThat(result)
                .extracting(RecommendationDto::bookId)
                .containsExactlyInAnyOrder(2L, 4L);

        assertThat(result)
                .anySatisfy(r -> {
                    assertThat(r.bookId()).isEqualTo(2L);
                    assertThat(r.title()).isEqualTo("T2");
                    assertThat(r.author()).isEqualTo("A2");
                })
                .anySatisfy(r -> {
                    assertThat(r.bookId()).isEqualTo(4L);
                    assertThat(r.title()).isEqualTo("T4");
                    assertThat(r.author()).isEqualTo("A4");
                });
    }

    @Test
    void shouldReturnEmptyWhenUserReviewedAllBooks() {
        Long userId = 7L;

        when(reviewClient.getReviewsByUser(userId)).thenReturn(List.of(
                new ReviewDto(1L, 1L, userId, 4, "ok"),
                new ReviewDto(2L, 2L, userId, 5, "wow")
        ));
        when(bookClient.getAllBooks()).thenReturn(List.of(
                new BookDto(1L, "B1", "A1", null, null),
                new BookDto(2L, "B2", "A2", null, null)
        ));

        List<RecommendationDto> result = recommendationService.getRecommendationsForUser(userId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllWhenUserHasNoReviews() {
        Long userId = 99L;

        when(reviewClient.getReviewsByUser(userId)).thenReturn(List.of());
        when(bookClient.getAllBooks()).thenReturn(List.of(
                new BookDto(5L, "Clean Code", "Robert C. Martin", null, null),
                new BookDto(6L, "Effective Java", "Joshua Bloch", null, null)
        ));

        List<RecommendationDto> result = recommendationService.getRecommendationsForUser(userId);

        assertThat(result)
                .extracting(RecommendationDto::bookId)
                .containsExactlyInAnyOrder(5L, 6L);
    }
}
