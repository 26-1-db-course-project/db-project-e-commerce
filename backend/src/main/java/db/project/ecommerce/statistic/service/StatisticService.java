package db.project.ecommerce.statistic.service;

import db.project.ecommerce.order.repository.OrderRepository;
import db.project.ecommerce.product.repository.ProductDetailRepository;
import db.project.ecommerce.statistic.dto.projection.CategorySalesItemProjection;
import db.project.ecommerce.statistic.dto.projection.ProductSalesItemProjection;
import db.project.ecommerce.statistic.dto.projection.SalesItemProjection;
import db.project.ecommerce.statistic.dto.request.SalesSearchRequest;
import db.project.ecommerce.statistic.dto.response.*;
import db.project.ecommerce.statistic.enums.PeriodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final OrderRepository orderRepository;
    private final ProductDetailRepository productDetailRepository;


    @Transactional(readOnly = true)
    //by Claude.
    public PeriodSalesListResponse getSalesStats(SalesSearchRequest request) {
        PeriodType period = request.getPeriod();
        LocalDate reqStart = request.getStartDate();
        LocalDate reqEnd = request.getEndDate();

        // 날짜(LocalDate) 입력을 조회용 LocalDateTime 범위로 변환.
        // 종료일을 포함하기 위해 종료일 다음 날 0시 미만(< endExclusive)으로 조회한다.
        LocalDateTime startDate = (reqStart == null) ? null : reqStart.atStartOfDay();
        LocalDateTime endDate = (reqEnd == null) ? null : reqEnd.atStartOfDay();
        LocalDateTime endExclusive = (reqEnd == null) ? null : reqEnd.plusDays(1).atStartOfDay();

        List<SalesItemProjection> projections = switch (period) {
            case DAILY -> orderRepository.getDailySales(startDate, endExclusive);
            case YEARLY -> orderRepository.getYearlySales(startDate, endExclusive);
            case MONTHLY -> orderRepository.getMonthlySales(startDate, endExclusive);
        };

        List<SalesResponse> items = projections.stream()
                .map(p -> SalesResponse.builder()
                        .label(p.getLabel())
                        .revenue(p.getRevenue())
                        .orderCount(p.getOrderCount())
                        .build())
                .toList();

        long totalRevenue = items.stream()
                .mapToLong(i -> i.getRevenue() == null ? 0L : i.getRevenue())
                .sum();
        long totalOrders = items.stream()
                .mapToLong(i -> i.getOrderCount() == null ? 0L : i.getOrderCount())
                .sum();

        return PeriodSalesListResponse.builder()
                .periodType(period)
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .items(items)
                .build();
    }

    @Transactional(readOnly = true)
    public ProductSalesListResponse getProductSalesStats() {
        List<ProductSalesItemProjection> rawItems = productDetailRepository.getProductSales();

        List<ProductSalesResponse> items = new ArrayList<>();
        for (ProductSalesItemProjection rawItem : rawItems) {
            ProductSalesResponse p = ProductSalesResponse.of(rawItem.getRank(), rawItem.getProductId(), rawItem.getProductName(),
                    rawItem.getCategoryName(), rawItem.getSoldQuantity(), rawItem.getRevenue());
            items.add(p);
        }

        return ProductSalesListResponse.of(items);
    }

    //By Gemini.
    @Transactional(readOnly = true)
    public CategorySalesListResponse getCategorySalesStats() {
        List<CategorySalesItemProjection> rawItems = productDetailRepository.getCategorySales();

        long totalRevenue = rawItems.stream()
                .mapToLong(CategorySalesItemProjection::getRevenue)
                .sum();

        List<CategorySalesResponse> items = rawItems.stream().map(stat -> {
            double ratio = totalRevenue > 0 ?
                    Math.round(((double) stat.getRevenue() / totalRevenue * 100) * 10) / 10.0 : 0.0;

            return CategorySalesResponse.builder()
                    .categoryName(stat.getCategoryName())
                    .soldQuantity(stat.getSoldQuantity())
                    .revenue(stat.getRevenue())
                    .revenueRatio(ratio)
                    .build();
        }).toList();

        return CategorySalesListResponse.builder()
                .totalRevenue(totalRevenue)
                .items(items)
                .build();
    }
}