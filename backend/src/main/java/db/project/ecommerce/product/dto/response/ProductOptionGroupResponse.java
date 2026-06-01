package db.project.ecommerce.product.dto.response;

import db.project.ecommerce.product.domain.option.OptionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ProductOptionGroupResponse {
    private Long productId;
    private List<OptionGroup> optionGroups;

    public static ProductOptionGroupResponse of(Long productId, List<OptionDetail> optionDetails) {
        List<OptionGroup> groups = optionDetails.stream()
                .collect(Collectors.groupingBy(
                        od -> od.getOptionType().getOptionTypeName(),
                        LinkedHashMap::new,
                        Collectors.toList()))
                .entrySet().stream()
                .map(e -> OptionGroup.builder()
                        .optionTypeName(e.getKey())
                        .values(e.getValue().stream()
                                .map(od -> new OptionValue(od.getOptionDetailId(), od.getOptionDetailName()))
                                .toList())
                        .build())
                .toList();

        return ProductOptionGroupResponse.builder()
                .productId(productId)
                .optionGroups(groups)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class OptionGroup {
        private String optionTypeName;
        private List<OptionValue> values;
    }

    @Getter
    @AllArgsConstructor
    public static class OptionValue {
        private Long optionDetailId;
        private String optionDetailName;
    }
}
