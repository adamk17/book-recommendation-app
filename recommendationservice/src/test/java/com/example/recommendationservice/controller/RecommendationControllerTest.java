package com.example.recommendationservice.controller;

import com.example.recommendationservice.dto.RecommendationDto;
import com.example.recommendationservice.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        RecommendationService recommendationService() {
            return mock(RecommendationService.class);
        }
    }

    @Test
    void shouldReturnRecommendationsForUser() throws Exception {
        var rec = new RecommendationDto(1L, "Clean Code", "Robert C. Martin");
        when(recommendationService.getRecommendationsForUser(42L))
                .thenReturn(List.of(rec));

        mockMvc.perform(get("/api/v1/recommendations/{userId}", 42L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"));

        verify(recommendationService, times(1)).getRecommendationsForUser(42L);
    }
}
