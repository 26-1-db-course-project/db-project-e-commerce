package db.project.ecommerce.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {
    @NotNull
    private Long manufacturerId;
    @NotNull
    private String productName;
    @NotNull
    private int price;
    @NotNull
    private Long categoryId;
    private String imageUrl;
}