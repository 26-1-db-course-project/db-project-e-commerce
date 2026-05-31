package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import db.project.ecommerce.member.domain.Address;
import lombok.Getter;

@Getter
public class AddAddressResponse {
    private final Long addressId;
    private final String city;
    private final String district;

    @JsonProperty("detail")
    private final String detailAddress;

    private final String message;

    public AddAddressResponse(Address address) {
        this.addressId = address.getAddressId();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.detailAddress = address.getDetailAddress();
        this.message = "주소 추가가 성공적으로 완료되었습니다";
    }
}