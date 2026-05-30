package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import db.project.ecommerce.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {
    private final String id;
    private final String email;
    private final String password;

    private final String phoneNumber;

    private final String memberType;
    private final AddressResponse address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime joinedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime updatedTime;

    public MemberResponse(Member member) {
        this.id = member.getLoginId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.memberType = member.getGrade().getGradeName();
        this.address = member.getAddresses().isEmpty() ? null : new AddressResponse(member.getAddresses().get(0));
        this.joinedDate = member.getCreatedAt();
        this.updatedTime = member.getModifiedAt();
    }
}
