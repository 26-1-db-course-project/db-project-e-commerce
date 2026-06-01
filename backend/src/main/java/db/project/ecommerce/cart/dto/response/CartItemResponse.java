package db.project.ecommerce.cart.dto.response;

import db.project.ecommerce.cart.domain.CartItem;

public record CartItemResponse(
        Long productDetailId,
        String productName,
        String optionValue,
        Long quantity,
        Long price
) {
    public static CartItemResponse from(CartItem cartItem, String optionValue) {
        // 상품 기본 가격 + 상세 옵션에 붙는 추가 금액(surcharge) 계산
        Long finalPrice = cartItem.getProductDetail().getProduct().getPrice()
                + cartItem.getProductDetail().getSurcharge();

        return new CartItemResponse(
                cartItem.getProductDetail().getId(),
                cartItem.getProductDetail().getProduct().getProductName(),
                optionValue, // 옵션 문자열은 밖에서 만들어서 주입받습니다.
                cartItem.getQuantity(),
                finalPrice
        );
    }
}