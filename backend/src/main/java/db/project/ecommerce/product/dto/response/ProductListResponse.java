package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> productResponseList;
    private Long productCount;

    public static ProductListResponse of (List<Product> products) {
        return ProductListResponse.builder()
                .productResponseList(products.stream().map(ProductResponse::of).toList())
                .productCount((long) products.size())
                .build();
    }
}
