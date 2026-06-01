package db.project.ecommerce.product.domain;

import db.project.ecommerce.product.domain.option.OptionDetail;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_option")
@NoArgsConstructor
public class ProductOption {
    @EmbeddedId
    private ProductOptionId id;

    @ManyToOne
    @MapsId("productDetailId")
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @ManyToOne
    @MapsId("optionDetailId")
    @JoinColumn(name = "option_detail_id")
    private OptionDetail optionDetail;

    @Builder
    public ProductOption (ProductDetail productDetail, OptionDetail optionDetail) {
        this.productDetail = productDetail;
        this.optionDetail = optionDetail;
        this.id = new ProductOptionId();
    }
}
