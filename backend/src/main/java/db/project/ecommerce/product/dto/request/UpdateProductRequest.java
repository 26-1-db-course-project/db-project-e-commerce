package db.project.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private Long stockQuantity;
    private Long surcharge;
    private Long sales;
    private String imageUrl;
}
