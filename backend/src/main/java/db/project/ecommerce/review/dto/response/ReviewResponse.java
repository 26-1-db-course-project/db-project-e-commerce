package db.project.ecommerce.review.dto.response;

import db.project.ecommerce.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Long memberId;
    private Long productDetailId;
    private int rating;
    private String content;

    public static ReviewResponse of (Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .memberId(review.getMember().getId())
                .productDetailId(review.getProductDetail().getId())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }
}
