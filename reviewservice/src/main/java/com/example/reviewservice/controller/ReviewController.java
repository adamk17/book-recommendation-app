package com.example.reviewservice.controller;

import com.example.reviewservice.dto.EnrichedReviewDto;
import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService service) {
        this.reviewService = service;
    }

    @PreAuthorize("hasRole('ADMIN') or @jwt.isSelf(#reviewDto.userId, authentication)")
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody @Valid ReviewDto reviewDto) {
        Review review = reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PreAuthorize("hasRole('ADMIN') or @ownership.check(#id, authentication)")
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/enriched")
    public ResponseEntity<List<EnrichedReviewDto>> getAllEnrichedReviews() {
        return ResponseEntity.ok(reviewService.getAllEnrichedReviews());
    }

    @PreAuthorize("hasRole('ADMIN') or @ownership.check(#id, authentication)")
    @GetMapping("/enriched/{id}")
    public ResponseEntity<EnrichedReviewDto> getEnrichedReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getEnrichedReviewById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or @ownership.check(#id, authentication)")
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id,  @RequestBody @Valid ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDto));
    }

    @PreAuthorize("hasRole('ADMIN') or @ownership.check(#id, authentication)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or @jwt.isSelf(#userId, authentication)")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }
}

