package db.project.ecommerce.cart.dto.request;

import db.project.ecommerce.cart.domain.Cart;
import db.project.ecommerce.cart.domain.CartItem;
import db.project.ecommerce.product.domain.ProductDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCartItemRequest {

    @NotNull(message = "상품 상세 ID는 필수입니다.")
    private Long productDetailId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Long quantity;

    public CartItem toEntity(Cart cart, ProductDetail productDetail) {
        return CartItem.builder()
                .cart(cart)
                .productDetail(productDetail)
                .quantity(this.quantity)
                .build();
    }
}