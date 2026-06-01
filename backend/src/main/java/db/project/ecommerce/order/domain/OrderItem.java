package db.project.ecommerce.order.domain;

import db.project.ecommerce.product.domain.ProductDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "order_price", nullable = false)
    private Long orderPrice;

    @Builder
    public OrderItem(Orders order, ProductDetail productDetail, OrderStatus status, Long quantity, Long orderPrice) {
        this.order = order;
        this.productDetail = productDetail;
        this.status = status;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
    }
}