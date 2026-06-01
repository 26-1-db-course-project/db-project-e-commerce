package db.project.ecommerce.cart.dto.response;

import db.project.ecommerce.cart.domain.Cart;

import java.util.List;

public record CartItemListResponse(
        Long memberId,
        Long totalPrice,
        List<CartItemResponse> cartItems
) {
    public static CartItemListResponse of(Cart cart, List<CartItemResponse> cartItemResponses) {

        // 💡 Java 8 Stream API를 이용해 총액(수량 * 가격) 계산
        Long totalPrice = cartItemResponses.stream()
                .mapToLong(item -> item.price() * item.quantity())
                .sum();

        return new CartItemListResponse(
                cart.getMember().getId(), // 혹은 cart.getMemberId()
                totalPrice,
                cartItemResponses
        );
    }
}