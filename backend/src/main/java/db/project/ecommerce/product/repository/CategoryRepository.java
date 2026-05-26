package db.project.ecommerce.product.repository;

import db.project.ecommerce.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
