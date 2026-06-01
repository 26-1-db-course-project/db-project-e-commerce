package db.project.ecommerce.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateReviewRequest {
    private Long memberID;
    private Long productDetailId;
    private int rating;
    private String content;
}
