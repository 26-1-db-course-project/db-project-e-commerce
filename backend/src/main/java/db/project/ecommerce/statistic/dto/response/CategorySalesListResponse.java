package db.project.ecommerce.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CategorySalesListResponse {
    private List<CategorySalesResponse> items;
    private Long totalRevenue;

    public static CategorySalesListResponse of(List<CategorySalesResponse> items, Long totalRevenue) {
        return CategorySalesListResponse.builder()
                .items(items)
                .totalRevenue(totalRevenue)
                .build();
    }
}
