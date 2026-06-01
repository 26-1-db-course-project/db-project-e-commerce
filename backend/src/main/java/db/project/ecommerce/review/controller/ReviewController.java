package db.project.ecommerce.review.controller;

import db.project.ecommerce.review.dto.response.ReviewResponse;
import db.project.ecommerce.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/product-details/{productDetailId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable("productDetailId") Long productDetailId,
                                                       @RequestParam("auth-id")Long memberId) {
        ReviewResponse response = reviewService.createReview(memberId, productDetailId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
