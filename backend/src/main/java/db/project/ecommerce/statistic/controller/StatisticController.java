package db.project.ecommerce.statistic.controller;

import db.project.ecommerce.statistic.dto.request.SalesSearchRequest;
import db.project.ecommerce.statistic.dto.response.CategorySalesListResponse;
import db.project.ecommerce.statistic.dto.response.PeriodSalesListResponse;
import db.project.ecommerce.statistic.dto.response.ProductSalesListResponse;
import db.project.ecommerce.statistic.enums.PeriodType;
import db.project.ecommerce.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/stats/sales")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<PeriodSalesListResponse> getSalesStats(
            @RequestParam(value = "period", defaultValue = "MONTHLY") PeriodType period,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        SalesSearchRequest request = new SalesSearchRequest(period, startDate, endDate);
        PeriodSalesListResponse response = statisticService.getSalesStats(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductSalesListResponse> getProductSaleStats() {
        ProductSalesListResponse response = statisticService.getProductSalesStats();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategorySalesListResponse> getCategorySalesStats() {
        CategorySalesListResponse response = statisticService.getCategorySalesStats();

        return ResponseEntity.ok(response);
    }
}
