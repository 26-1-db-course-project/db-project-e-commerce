package db.project.ecommerce.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductSalesListResponse {
    private List<ProductSalesResponse> items;
    private Long productTotal;

    public static ProductSalesListResponse of (List<ProductSalesResponse> items) {
        return ProductSalesListResponse.builder()
                .items(items)
                .productTotal((long)items.size())
                .build();
    }
}