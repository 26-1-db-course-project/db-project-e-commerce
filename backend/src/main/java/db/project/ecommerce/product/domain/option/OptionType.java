package db.project.ecommerce.product.domain.option;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option_type")
public class OptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_type_id", nullable = false)
    private Long optionTypeId;

    @Column(name = "option_type_name", nullable = false)
    private String optionTypeName;

    @Builder
    public OptionType (String optionType) {
        this.optionTypeName = optionType;
    }
}
