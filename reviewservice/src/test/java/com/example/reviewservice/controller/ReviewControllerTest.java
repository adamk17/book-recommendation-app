package com.example.reviewservice.controller;

import com.example.reviewservice.dto.EnrichedReviewDto;
import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ReviewService reviewService() {
            return mock(ReviewService.class);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllReviews() throws Exception {
        Review review = Review.builder()
                .id(1L).bookId(1L).userId(2L).rating(5).comment("Great!")
                .createdAt(LocalDateTime.now()).build();
        when(reviewService.getAllReviews()).thenReturn(List.of(review));

        mockMvc.perform(get("/api/v1/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnReviewById() throws Exception {
        Review review = Review.builder()
                .id(1L).bookId(1L).userId(2L).rating(4).comment("Nice")
                .createdAt(LocalDateTime.now()).build();
        when(reviewService.getReviewById(1L)).thenReturn(review);

        mockMvc.perform(get("/api/v1/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateReview() throws Exception {
        ReviewDto dto = new ReviewDto(1L, 2L, 5, "Excellent!");
        Review saved = Review.builder()
                .id(1L).bookId(1L).userId(2L).rating(5).comment("Excellent!")
                .createdAt(LocalDateTime.now()).build();
        when(reviewService.createReview(any())).thenReturn(saved);

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateReview() throws Exception {
        ReviewDto dto = new ReviewDto(1L, 2L, 3, "Updated comment");
        Review updated = Review.builder()
                .id(1L).bookId(1L).userId(2L).rating(3).comment("Updated comment")
                .createdAt(LocalDateTime.now()).build();
        when(reviewService.updateReview(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/v1/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Updated comment"))
                .andExpect(jsonPath("$.rating").value(3));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteReview() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);

        mockMvc.perform(delete("/api/v1/reviews/1"))
                .andExpect(status().isNoContent());

        verify(reviewService, times(1)).deleteReview(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllEnrichedReviews() throws Exception {
        EnrichedReviewDto enriched = new EnrichedReviewDto(
                1L,
                10L,
                "Book Title",
                "Book Author",
                20L,
                "john_doe",
                "john@example.com",
                5,
                "Awesome book!",
                LocalDateTime.now()
        );

        when(reviewService.getAllEnrichedReviews()).thenReturn(List.of(enriched));

        mockMvc.perform(get("/api/v1/reviews/enriched"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].bookId").value(10L))
                .andExpect(jsonPath("$[0].bookTitle").value("Book Title"))
                .andExpect(jsonPath("$[0].userId").value(20L))
                .andExpect(jsonPath("$[0].username").value("john_doe"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEnrichedReviewById() throws Exception {
        EnrichedReviewDto enriched = new EnrichedReviewDto(
                2L,
                11L,
                "Another Book",
                "Author Name",
                21L,
                "alice",
                "alice@example.com",
                4,
                "Very good",
                LocalDateTime.now()
        );

        when(reviewService.getEnrichedReviewById(2L)).thenReturn(enriched);

        mockMvc.perform(get("/api/v1/reviews/enriched/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.bookId").value(11L))
                .andExpect(jsonPath("$.bookTitle").value("Another Book"))
                .andExpect(jsonPath("$.userId").value(21L))
                .andExpect(jsonPath("$.username").value("alice"));
    }
}
