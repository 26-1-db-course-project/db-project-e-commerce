package db.project.ecommerce.order.repository;

import db.project.ecommerce.order.domain.Orders;
import db.project.ecommerce.statistic.dto.projection.SalesItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = """
        SELECT DATE_FORMAT(order_date, '%Y-%m') AS label,
               SUM(total_price) AS revenue,
               COUNT(order_id) AS orderCount
        FROM orders
        WHERE order_date >= :startDate AND order_date <= :endDate
        GROUP BY label
        ORDER BY label
    """, nativeQuery = true)
    List<SalesItemProjection> getMonthlySales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
        SELECT DATE_FORMAT(order_date, '%Y') AS label,
               SUM(total_price) AS revenue,
               COUNT(order_id) AS orderCount
        FROM orders
        WHERE order_date >= :startDate AND order_date <= :endDate
        GROUP BY label
        ORDER BY label
    """, nativeQuery = true)
    List<SalesItemProjection> getYearlySales(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);

    @Query(value = """
        SELECT DATE_FORMAT(order_date, '%Y-%m-%d') AS label,
               SUM(total_price) AS revenue,
               COUNT(order_id) AS orderCount
        FROM orders
        WHERE order_date >= :startDate AND order_date <= :endDate
        GROUP BY label
        ORDER BY label
    """, nativeQuery = true)
    List<SalesItemProjection> getDailySales(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
