package db.project.ecommerce.cart.dto.response;

import java.util.List;

public record CartItemListResponse(
        Long memberId,
        Long totalPrice,
        List<CartItemResponse> cartItems
) {
}