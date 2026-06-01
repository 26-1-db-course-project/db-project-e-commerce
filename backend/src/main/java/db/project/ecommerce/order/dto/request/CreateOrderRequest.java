package db.project.ecommerce.order.dto.request;

import java.util.List;

public record CreateOrderRequest (
        Long memberId,
        Long deliveryAddressId,
        List<Long> selectedProductDetailIds
) {
}
