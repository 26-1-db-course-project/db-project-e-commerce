package db.project.ecommerce.cart.domain;

import db.project.ecommerce.global.domain.BaseEntity;
import db.project.ecommerce.product.domain.ProductDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "cart_item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "product_detail_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(nullable = false)
    private Long quantity;

    @Builder
    public CartItem(Cart cart, ProductDetail productDetail, Long quantity) {
        this.cart = cart;
        this.productDetail = productDetail;
        this.quantity = (quantity != null) ? quantity : 1L; // 기본값 1
    }

    // 수량 변경 기능
    public void updateQuantity(Long quantity) {
        this.quantity = quantity;
    }

    // 기존 장바구니에 똑같은 상품을 또 담았을 때 수량 증가
    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }
}