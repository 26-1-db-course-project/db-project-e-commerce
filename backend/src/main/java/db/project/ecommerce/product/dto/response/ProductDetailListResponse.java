package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductDetailListResponse {
    private List<ProductDetailResponse> productDetailResponseList;

    public static ProductDetailListResponse of(List<ProductDetail> productDetails) {
        return ProductDetailListResponse.builder()
                .productDetailResponseList(productDetails.stream().map(ProductDetailResponse::of).toList())
                .build();
    }
}
