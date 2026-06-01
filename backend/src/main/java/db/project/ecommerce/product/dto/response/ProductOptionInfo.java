package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductOptionInfo {
    private Long optionDetailId;
    private String optionTypeName;
    private String optionDetailName;

    public static ProductOptionInfo of (ProductOption productOption) {
        return ProductOptionInfo.builder()
                .optionDetailId(productOption.getOptionDetail().getOptionDetailId())
                .optionDetailName(productOption.getOptionDetail().getOptionDetailName())
                .optionTypeName(productOption.getOptionDetail().getOptionType().getOptionTypeName())
                .build();
    }
}
