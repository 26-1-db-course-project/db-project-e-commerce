package db.project.ecommerce.review.domain;

import db.project.ecommerce.global.domain.BaseEntity;
import db.project.ecommerce.member.domain.Member;
import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductDetail;
import db.project.ecommerce.review.ReviewStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "review_content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_status", nullable = false)
    private ReviewStatus status;

    @Builder
    public Review (Member member, ProductDetail productDetail, int rating, String content) {
        this.member = member;
        this.productDetail = productDetail;
        this.rating = rating;
        this.content = content;
    }
}
