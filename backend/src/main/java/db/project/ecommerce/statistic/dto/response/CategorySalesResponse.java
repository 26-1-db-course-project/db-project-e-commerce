package db.project.ecommerce.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategorySalesResponse {
    private String categoryName;
    private Long soldQuantity;
    private Long revenue;
    private double revenueRatio;

    public static CategorySalesResponse of (String categoryName, Long soldQuantity, Long revenue, double revenueRatio) {
        return CategorySalesResponse.builder()
                .categoryName(categoryName)
                .soldQuantity(soldQuantity)
                .revenue(revenue)
                .revenueRatio(revenueRatio)
                .build();
    }
}
