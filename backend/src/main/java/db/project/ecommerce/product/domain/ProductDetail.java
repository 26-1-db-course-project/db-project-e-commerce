package db.project.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

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
    @OnDelete(action = OnDeleteAction.CASCADE) //product(부모) 지우면 전부 지워지게 설정.
    private Product product;

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity;

    @Column(name = "surcharge", nullable = false)
    private Long surcharge = 0L;

    @Column(name = "sales", nullable = false)
    private Long sales = 0L;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    //AI 생성 요소
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

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

    //TODO: 업데이트 관련 함수 추가 (수량 변화, 추가가격 변화, sales 변화)
    public void changeStock(Long quantity) {
        if (quantity < 0 ) {
            throw new IllegalArgumentException("재고는 음수일 수 없습니다.");
        }
        this.stockQuantity = quantity;
    }

    public void increaseSales (Long quantity) {
        this.sales += quantity;
    }

    public void decreaseSales (Long quantity) {
        if (this.sales - quantity < 0) {
            throw new IllegalArgumentException("판매량은 음수일 수 없습니다.");
        }
    }

    public void updateProductDetail(Long stockQuantity, Long surcharge, Long sales, String imageUrl) {
        if (stockQuantity!=null) this.stockQuantity = stockQuantity;
        if(surcharge!=null) this.surcharge = surcharge;
        if(sales!=null) this.sales = sales;
        if(imageUrl!=null) this.imageUrl = imageUrl;
    }
}
// By Gemini.