package db.project.ecommerce.review.repository;

import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r FROM Review r
            WHERE r.productDetail.product = :product
              AND r.status <> db.project.ecommerce.review.enums.ReviewStatus.DELETED
            """)
    List<Review> findAllByProduct(@Param("product") Product product);
}
