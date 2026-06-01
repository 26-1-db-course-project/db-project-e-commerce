package db.project.ecommerce.order.repository;

import db.project.ecommerce.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
