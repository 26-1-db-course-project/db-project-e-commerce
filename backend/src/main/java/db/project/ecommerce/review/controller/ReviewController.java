package db.project.ecommerce.review.controller;

import db.project.ecommerce.review.dto.request.CreateReviewRequest;
import db.project.ecommerce.review.dto.request.UpdateReviewRequest;
import db.project.ecommerce.review.dto.response.ReviewListResponse;
import db.project.ecommerce.review.dto.response.ReviewResponse;
import db.project.ecommerce.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //TODO: 리뷰 생성
    @PostMapping("/product-details/{productDetailId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable("productDetailId") Long productDetailId,
                                                       @RequestParam("auth-id")Long memberId,
                                                       @RequestBody CreateReviewRequest request) {
        ReviewResponse response = reviewService.createReview(memberId, productDetailId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //TODO: 상품별(SKU 아닌 상위 단위) 리뷰 조회
    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<ReviewListResponse> getProductReviews(@PathVariable("productId")Long productId) {
        ReviewListResponse response = reviewService.getProductReviews(productId);

        return ResponseEntity.ok(response);
    }

    //TODO: 리뷰 수정
    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable("reviewId")Long reviewId,
                                                       @RequestParam("auth-id")Long memberId,
                                                       @RequestBody UpdateReviewRequest request) {
        ReviewResponse response = reviewService.updateReview(reviewId, memberId, request);

        return ResponseEntity.ok(response);
    }

    //TODO: 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable("reviewId")Long reviewId,
                                                       @RequestParam("auth-id")Long memberId) {
        reviewService.deleteReview(reviewId, memberId);

        return ResponseEntity.noContent().build();
    }

}
