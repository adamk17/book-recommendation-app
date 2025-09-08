package com.example.recommendationservice.controller;

import com.example.recommendationservice.dto.RecommendationDto;
import com.example.recommendationservice.service.RecommendationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PreAuthorize("hasRole('ADMIN') or @jwt.isSelf(#userId, authentication)")
    @GetMapping("/{userId}")
    public List<RecommendationDto> getRecommendations(@PathVariable Long userId) {
        return recommendationService.getRecommendationsForUser(userId);
    }
}
