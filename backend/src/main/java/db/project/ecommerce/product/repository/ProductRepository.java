package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Category;
import db.project.ecommerce.product.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category, Sort sort);
    @Query("select p from Product p where p.productName LIKE %:keyword%" )
    List<Product> searchByName(@Param("keyword")String keyword, Sort sort);
}
