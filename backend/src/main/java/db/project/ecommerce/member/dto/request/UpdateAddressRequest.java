package db.project.ecommerce.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAddressRequest {

    @JsonProperty("address_id")
    private Long addressId;

    private String city;
    private String district;

    @JsonProperty("detail")
    private String detailAddress;
}
