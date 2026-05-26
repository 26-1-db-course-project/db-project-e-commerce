package db.project.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Long id;

    @Column (name = "company_name")
    private String company;

    @Column (name = "owner")
    private String owner;

    @Builder
    public Manufacturer(String company, String owner) {
        this.company = company;
        this.owner = owner;
    }
}
