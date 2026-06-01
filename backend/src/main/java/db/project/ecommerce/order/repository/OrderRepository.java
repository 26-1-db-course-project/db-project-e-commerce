package db.project.ecommerce.order.repository;

import db.project.ecommerce.order.domain.Orders;
import db.project.ecommerce.statistic.dto.projection.SalesItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query(value = "CALL CheckoutPartialCart(:memberId, :deliveryAddressId, :selectedItemIds)", nativeQuery = true)
    String callCheckoutPartialCart(
            @Param("memberId") Long memberId,
            @Param("deliveryAddressId") Long deliveryAddressId,
            @Param("selectedItemIds") String selectedItemIds // "101,205" 형태의 문자열
    );

    @Query("SELECT DISTINCT o FROM Orders o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.productDetail pd " +
            "JOIN FETCH pd.product " +
            "JOIN FETCH oi.status " +
            "WHERE o.id = :orderId AND o.member.id = :memberId") // Member 엔티티 매핑 상태에 따라 o.member.id 로 수정 필요할 수 있음
    Optional<Orders> findOrderDetailByIdAndMemberId(@Param("orderId") Long orderId, @Param("memberId") Long memberId);

    // 특정 회원의 특정 기간 이후 주문 내역 전체 조회 (최신순 정렬)
    List<Orders> findByMember_IdAndOrderDateAfterOrderByOrderDateDesc(Long memberId, LocalDateTime startDate);
  
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
