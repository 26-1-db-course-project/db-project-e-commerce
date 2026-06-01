package db.project.ecommerce.statistic.controller;

import db.project.ecommerce.statistic.dto.request.SalesSearchRequest;
import db.project.ecommerce.statistic.dto.response.CategorySalesListResponse;
import db.project.ecommerce.statistic.dto.response.PeriodSalesListResponse;
import db.project.ecommerce.statistic.dto.response.ProductSalesListResponse;
import db.project.ecommerce.statistic.service.StatisticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stats/sales")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<PeriodSalesListResponse> getSalesStats(@Valid @RequestBody SalesSearchRequest request) {
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
