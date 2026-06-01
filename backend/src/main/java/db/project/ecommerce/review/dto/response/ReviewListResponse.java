package db.project.ecommerce.review.dto.response;

import db.project.ecommerce.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReviewListResponse {
    private Long reviewCount;
    private List<ReviewResponse> reviewList;

    public static ReviewListResponse of (List<Review> reviews) {
        return ReviewListResponse.builder()
                .reviewList(reviews.stream().map(ReviewResponse::of).toList())
                .reviewCount((long) reviews.size())
                .build();
    }
}
