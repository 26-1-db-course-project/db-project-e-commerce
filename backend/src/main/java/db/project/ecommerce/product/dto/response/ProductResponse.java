package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private int price;
    private String manufacturer;
    private String category;
    private String imageUrl;

    public static ProductResponse of (Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory().getCategory())
                .manufacturer(product.getManufacturer().getCompany())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
