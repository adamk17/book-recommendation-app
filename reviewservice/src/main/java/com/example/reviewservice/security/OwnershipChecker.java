package com.example.reviewservice.security;

import com.example.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ownership")
@RequiredArgsConstructor
public class OwnershipChecker {
    private final ReviewService reviewService;
    private final JwtFacade jwt;

    public boolean check(Long reviewId, Authentication auth) {
        Long caller = jwt.extractUserId(auth);
        if (caller == null || reviewId == null) return false;

        Long owner = reviewService.getReviewOwnerId(reviewId);
        return owner != null && owner.equals(caller);
    }
}