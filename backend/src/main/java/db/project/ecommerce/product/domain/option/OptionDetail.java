package db.project.ecommerce.product.domain.option;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "option_detail")
public class OptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_detail_id", nullable = false)
    private Long optionDetailId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "option_type_id")
    private OptionType optionType;

    @Column(name = "option_value", nullable = false)
    private String optionDetailName;

    @Builder
    public OptionDetail (OptionType optionType, String optionValue) {
        this.optionType = optionType;
        this.optionDetailName = optionValue;
    }
}
