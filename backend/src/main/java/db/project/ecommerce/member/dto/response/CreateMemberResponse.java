package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import db.project.ecommerce.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateMemberResponse {
    private final String id;
    private final String email;
    private final String password;

    private final String phoneNumber;

    private final String memberType;
    private final AddressResponse address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime joinedDate;

    private final String message;

    public CreateMemberResponse(Member member) {
        this.id = member.getLoginId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.memberType = member.getGrade().getGradeName();
        this.address = new AddressResponse(member.getAddresses().get(0));
        this.joinedDate = member.getCreatedAt();
        this.message = "멤버 생성이 성공적으로 완료되었습니다";
    }
}
