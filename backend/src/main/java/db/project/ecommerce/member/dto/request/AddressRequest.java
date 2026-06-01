package db.project.ecommerce.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import db.project.ecommerce.member.domain.Address;
import db.project.ecommerce.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressRequest {
    private String city;
    private String district;

    @JsonProperty("detail")
    private String detailAddress;

    public Address toEntity(Member member) {
        return Address.builder()
                .member(member)
                .city(city)
                .district(district)
                .detailAddress(detailAddress)
                .build();
    }
}
