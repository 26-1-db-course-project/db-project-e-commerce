package db.project.ecommerce.cart.repository;

import db.project.ecommerce.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_MemberIdAndProductDetail_Id(Long memberId, Long productDetailId);
}
