package db.project.ecommerce.statistic.dto.projection;

public interface ProductSalesItemProjection {
    Long getRank();
    Long getProductId();
    String getProductName();
    String getCategoryName();
    Long getSoldQuantity();
    Long getRevenue();
}
