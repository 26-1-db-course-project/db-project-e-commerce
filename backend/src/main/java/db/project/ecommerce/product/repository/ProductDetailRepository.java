package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Optional<ProductDetail> findByProductAndId(Product product, Long productDetailId);

    @Query("""
            SELECT DISTINCT pd FROM ProductDetail pd
            LEFT JOIN FETCH pd.productOptions po
            LEFT JOIN FETCH po.optionDetail od
            LEFT JOIN FETCH od.optionType
            WHERE pd.product = :product
            """)
    List<ProductDetail> findAllByProductWithOptions(@Param("product") Product product); //AI 작성

}