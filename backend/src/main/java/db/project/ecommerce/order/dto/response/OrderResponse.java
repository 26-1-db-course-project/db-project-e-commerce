package db.project.ecommerce.order.dto.response;

import java.time.LocalDateTime;

public record OrderResponse(
        Long orderId,
        LocalDateTime orderDate,
        String shippingAddress,
        Long totalPrice,
        String orderSummaryTitle
) {
}