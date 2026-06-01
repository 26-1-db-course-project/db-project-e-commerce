package db.project.ecommerce.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(
        Long orderId,
        LocalDateTime orderDate,
        String shippingAddress,
        Long totalPrice,
        List<OrderItemDto> orderItems
) {
    // 주문 상품 상세 내역
    public record OrderItemDto(
            Long productDetailId,
            String productName,
            Long quantity,
            Long orderPrice,
            String statusName
    ) {}
}