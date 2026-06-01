package db.project.ecommerce.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SalesResponse {
    private String label;
    private Long revenue;
    private Long orderCount;

}
