package db.project.ecommerce.statistic.dto.projection;

public interface CategorySalesItemProjection {
    String getCategoryName();
    Long getSoldQuantity();
    Long getRevenue();
    Long getRevenueRatio();
}
