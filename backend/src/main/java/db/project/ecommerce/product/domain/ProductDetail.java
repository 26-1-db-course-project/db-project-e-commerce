package db.project.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity;

    @Column(name = "surcharge", nullable = false)
    private Long surcharge = 0L;

    @Column(name = "sales", nullable = false)
    private Long sales = 0L;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Builder
    public ProductDetail(Product product, Long stockQuantity, Long surcharge, Long sales, String imageUrl) {
        this.product = product;
        this.stockQuantity = stockQuantity;
        this.surcharge = (surcharge != null) ? surcharge : 0L;
        this.sales = (sales != null) ? sales : 0L;
        this.imageUrl = imageUrl;
    }

    public void decreaseStock(Long quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }
}
// By Gemini.