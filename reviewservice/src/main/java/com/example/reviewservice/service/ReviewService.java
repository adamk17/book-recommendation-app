package com.example.reviewservice.service;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.exception.ReviewNotFoundException;
import com.example.reviewservice.repository.ReviewRepository;
import com.example.reviewservice.service.client.BookClient;
import com.example.reviewservice.service.client.UserClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserClient userClient;
    private final BookClient bookClient;

    public ReviewService(ReviewRepository repository, UserClient userClient, BookClient bookClient) {
        this.reviewRepository = repository;
        this.userClient = userClient;
        this.bookClient = bookClient;
    }

    public Review createReview(ReviewDto dto) {
        userClient.getUserById(dto.userId());
        bookClient.getBookById(dto.bookId());

        Review review = Review.builder()
                .bookId(dto.bookId())
                .userId(dto.userId())
                .rating(dto.rating())
                .comment(dto.comment())
                .createdAt(LocalDateTime.now())
                .build();
        return reviewRepository.save(review);
    }


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    public Review updateReview(Long id, ReviewDto dto) {
        userClient.getUserById(dto.userId());
        bookClient.getBookById(dto.bookId());

        Review existing = getReviewById(id);
        existing.setUserId(dto.userId());
        existing.setBookId(dto.bookId());
        existing.setRating(dto.rating());
        existing.setComment(dto.comment());
        return reviewRepository.save(existing);
    }


    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException(id);
        }
        reviewRepository.deleteById(id);
    }
}
