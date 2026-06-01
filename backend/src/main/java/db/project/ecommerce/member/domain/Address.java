package db.project.ecommerce.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "delivery_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String city;
    private String district;

    @Column(name = "detail_address")
    private String detailAddress;

    @Builder
    public Address(Member member, String city, String district, String detailAddress) {
        this.member = member;
        this.city = city;
        this.district = district;
        this.detailAddress = detailAddress;
    }

    public void update(String city, String district, String detailAddress) {
        this.city = city;
        this.district = district;
        this.detailAddress = detailAddress;
    }
}
