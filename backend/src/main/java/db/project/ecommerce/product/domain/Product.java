package db.project.ecommerce.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column (name = "image_url")
    private String imageUrl;

    @Builder
    public Product(Manufacturer manufacturer, String name, int price, Category category, String imageUrl) {
        this.manufacturer = manufacturer;
        this.productName = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

}
