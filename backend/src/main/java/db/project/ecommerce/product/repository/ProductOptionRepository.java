package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Product;
import db.project.ecommerce.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

}
