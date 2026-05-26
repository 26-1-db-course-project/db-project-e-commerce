package db.project.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {
    private Long manufacturerId;
    private String productName;
    private int price;
    private Long categoryId;
    private String imageUrl;
}
