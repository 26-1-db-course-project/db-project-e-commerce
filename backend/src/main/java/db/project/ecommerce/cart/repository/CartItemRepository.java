package db.project.ecommerce.cart.repository;

import db.project.ecommerce.cart.domain.Cart;
import db.project.ecommerce.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_MemberIdAndProductDetail_Id(Long memberId, Long productDetailId);
    List<CartItem> findAllByCart(Cart cart);

    // 1. 장바구니 추가 프로시저 호출
    @Modifying // 데이터 변경(INSERT/UPDATE)이 일어나는 네이티브 쿼리에 필수!
    @Query(value = "CALL add_cart(:memberId, :productId, :quantity)", nativeQuery = true)
    void callAddCart(
            @Param("memberId") Long memberId,
            @Param("productId") Long productId,
            @Param("quantity") Integer quantity
    );

    // 2. 장바구니 총 결제 금액 계산 함수 호출
    @Query(value = "SELECT get_cart_price(:memberId)", nativeQuery = true)
    Integer getCartTotalPrice(@Param("memberId") Long memberId);
}
