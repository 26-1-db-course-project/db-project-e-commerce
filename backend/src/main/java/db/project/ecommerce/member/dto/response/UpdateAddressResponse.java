package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import db.project.ecommerce.member.domain.Address;
import db.project.ecommerce.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateAddressResponse {
    private final String id;
    private final AddressResponse address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime updatedTime;

    private final String message;

    public UpdateAddressResponse(Member member, Address address) {
        this.id = member.getLoginId();
        this.address = new AddressResponse(address);
        this.updatedTime = LocalDateTime.now();
        this.message = "주소 수정이 성공적으로 완료되었습니다";
    }
}
