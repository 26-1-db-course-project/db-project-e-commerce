package db.project.ecommerce.review.service;

import db.project.ecommerce.global.exception.CustomException;
import db.project.ecommerce.global.exception.ErrorCode;
import db.project.ecommerce.member.domain.Member;
import db.project.ecommerce.member.service.MemberService;
import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.product.service.ProductDetailService;
import db.project.ecommerce.review.domain.Review;
import db.project.ecommerce.review.dto.request.CreateReviewRequest;
import db.project.ecommerce.review.dto.request.UpdateReviewRequest;
import db.project.ecommerce.review.dto.response.ReviewListResponse;
import db.project.ecommerce.review.dto.response.ReviewResponse;
import db.project.ecommerce.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final MemberService memberService;
    private final ProductDetailService productDetailService;
    private final ReviewRepository reviewRepository;

    //TODO: 리뷰 생성
    @Transactional
    public ReviewResponse createReview(Long memberId, Long productDetailId, CreateReviewRequest request) {
        Member member = memberService.findMember(memberId);
        ProductDetail productDetail = productDetailService.getProductDetail(productDetailId);
        Review newReview = Review.builder()
                .member(member)
                .productDetail(productDetail)
                .rating(request.getRating())
                .content(request.getContent())
                .build();
        reviewRepository.save(newReview);

        return ReviewResponse.of(newReview);
    }

    //TODO: 상품별 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewListResponse getProductReviews(Long productId) {
        Product product = productDetailService.getProduct(productId);
        List<Review> reviews = reviewRepository.findAllByProduct(product);

        return ReviewListResponse.of(reviews);
    }

    //TODO: 리뷰 수정
    @Transactional
    public ReviewResponse updateReview(Long reviewId, Long memberId, UpdateReviewRequest request) {
        Review review = findReview(reviewId);
        Member member = memberService.findMember(memberId);
        authorizeWriter(review, member);

        review.updateReview(request.getRating(), request.getContent());

        return ReviewResponse.of(review);
    }

    //TODO: 리뷰 삭제
    public void deleteReview(Long reviewId, Long memberId) {
        Review review = findReview(reviewId);
        Member member = memberService.findMember(memberId);
        authorizeWriter(review, member);

        reviewRepository.delete(review);
    }

    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public void authorizeWriter(Review review, Member member) {
        if(!review.getMember().getId().equals(member.getId())){
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
