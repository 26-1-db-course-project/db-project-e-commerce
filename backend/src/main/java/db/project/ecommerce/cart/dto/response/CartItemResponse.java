package db.project.ecommerce.cart.dto.response;

public record CartItemResponse(
        Long productDetailId,
        String productName,
        String optionValue,
        Long quantity,
        Long price
) {
}