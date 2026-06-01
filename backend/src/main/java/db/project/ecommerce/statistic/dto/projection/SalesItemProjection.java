package db.project.ecommerce.statistic.dto.projection;

//네이티브 쿼리 사용 시, Object[]로 안받기 위해서 사용 => Gemini 도움.
public interface SalesItemProjection {
    String getLabel();
    Long getRevenue();
    Long getOrderCount();
}
