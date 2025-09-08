package com.example.recommendationservice.service.client;

import com.example.recommendationservice.config.FeignAuthConfig;
import com.example.recommendationservice.dto.ReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "review-service", url = "${app.reviews.url}", configuration = FeignAuthConfig.class)
public interface ReviewClient {
    @GetMapping("/api/v1/reviews/users/{userId}")
    List<ReviewDto> getReviewsByUser(@PathVariable Long userId);
}
