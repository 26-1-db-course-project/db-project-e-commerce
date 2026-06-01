package db.project.ecommerce.statistic.dto.response;

import db.project.ecommerce.statistic.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SalesListResponse {
    private PeriodType periodType;
    private Long startDate;
    private Long endDate;
    private Long totalRevenue;
    private Long totalOrders;
    private List<SalesResponse> items;
}
