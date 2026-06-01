package db.project.ecommerce.review.dto.request;

import db.project.ecommerce.review.enums.ReviewStatus;
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
    private String content;
}
