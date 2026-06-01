package db.project.ecommerce.statistic.dto.response;

import db.project.ecommerce.statistic.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PeriodSalesListResponse {
    private PeriodType periodType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalRevenue;
    private Long totalOrders;
    private List<SalesResponse> items;
}
