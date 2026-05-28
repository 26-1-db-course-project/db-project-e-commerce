package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

}