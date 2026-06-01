package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {
    private Long productDetailId;
    private Long productId;
    private Long stockQuantity;
    private Long surcharge;
    private Long sales;
    private String imageUrl;
    private List<ProductOptionInfo> options; //AI 생성 요소

    public static ProductDetailResponse of(ProductDetail productDetail) {

        return ProductDetailResponse.builder()
                .productId(productDetail.getProduct().getId())
                .productDetailId(productDetail.getId())
                .stockQuantity(productDetail.getStockQuantity())
                .surcharge(productDetail.getSurcharge())
                .sales(productDetail.getSales())
                .imageUrl(productDetail.getImageUrl())
                .options(productDetail.getProductOptions().stream().map(ProductOptionInfo::of).toList())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class OptionInfo {
        private String optionType;
        private String optionValue;
    }
}