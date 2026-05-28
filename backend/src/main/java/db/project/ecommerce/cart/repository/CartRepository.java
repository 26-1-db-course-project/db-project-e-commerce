package db.project.ecommerce.cart.repository;

import db.project.ecommerce.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
