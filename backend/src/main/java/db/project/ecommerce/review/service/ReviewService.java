package db.project.ecommerce.review.service;

import db.project.ecommerce.review.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final MemberService memberService;
    public ReviewResponse createReview(Long memberId, Long productDetailId) {
    }
}
