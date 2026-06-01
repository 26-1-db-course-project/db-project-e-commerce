package db.project.ecommerce.statistic.dto.response;

import db.project.ecommerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductSalesResponse {
    private Long rank;
    private Long productId;
    private String productName;
    private String categoryName;
    private Long soldQuantity;
    private Long revenue;

    public static ProductSalesResponse of (Long rank, Long productId, String productName, String categoryName, Long soldQuantity, Long revenue) {
        return ProductSalesResponse.builder()
                .rank(rank)
                .productId(productId)
                .productName(productName)
                .categoryName(categoryName)
                .soldQuantity(soldQuantity)
                .revenue(revenue)
                .build();
    }
}
