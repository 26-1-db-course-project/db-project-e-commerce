package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.option.OptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionDetailRepository extends JpaRepository<OptionDetail, Long> {
    Optional<OptionDetail> findById(Long optionDetailId);

    @Query("""
            SELECT DISTINCT od FROM ProductDetail pd
            JOIN pd.productOptions po
            JOIN po.optionDetail od
            JOIN FETCH od.optionType
            WHERE pd.product = :product
            """)
    List<OptionDetail> findDistinctOptionsByProduct(@Param("product") Product product);
}