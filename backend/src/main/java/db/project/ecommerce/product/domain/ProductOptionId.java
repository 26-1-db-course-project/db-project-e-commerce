package db.project.ecommerce.product.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductOptionId implements Serializable {
    private Long productDetailId;
    private Long optionDetailId;

    public ProductOptionId(){}
    public ProductOptionId(Long productDetailId, Long optionDetailId) {
        this.productDetailId = productDetailId;
        this.optionDetailId = optionDetailId;
    }


    //아래 hashCode, equals 오버라이딩 코드는 제미나이의 도움을 받았습니다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductOptionId that = (ProductOptionId) o;
        return Objects.equals(productDetailId, that.productDetailId) &&
                Objects.equals(optionDetailId, that.optionDetailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDetailId, optionDetailId);
    }
}
