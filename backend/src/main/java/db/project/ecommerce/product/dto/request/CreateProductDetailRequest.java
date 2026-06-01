package db.project.ecommerce.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDetailRequest {
    private Long stockQuantity;
    private Long surcharge;
    private String imageUrl;
    private List<Long> optionDetailId;
}
