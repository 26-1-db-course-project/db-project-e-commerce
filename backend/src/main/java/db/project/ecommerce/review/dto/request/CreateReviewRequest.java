package db.project.ecommerce.review.dto.request;

import db.project.ecommerce.review.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateReviewRequest {
    private Long memberID;
    private Long productDetailId;
    private int rating;
    private ReviewStatus status;
}
