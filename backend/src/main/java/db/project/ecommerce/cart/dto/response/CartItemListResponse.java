package db.project.ecommerce.cart.dto.response;

import db.project.ecommerce.cart.domain.Cart;

import java.util.List;

public record CartItemListResponse(
        Long memberId,
        Long totalPrice,
        List<CartItemResponse> cartItems
) {}