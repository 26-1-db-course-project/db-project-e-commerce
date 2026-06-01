package db.project.ecommerce.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private Long memberId;
    private Long productId;
    private int rating;
    private String content;
}
