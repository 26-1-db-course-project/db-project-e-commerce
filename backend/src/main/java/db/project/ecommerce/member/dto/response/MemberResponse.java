package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import db.project.ecommerce.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponse {
    private final Long memberId;
    private final String loginId;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String memberGrade;
    private final String activityStatus;
    private final AddressResponse address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime joinedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime updatedTime;

    public MemberResponse(Member member) {
        this.memberId = member.getId();
        this.loginId = member.getLoginId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.memberGrade  = member.getGrade().getGradeName();
        this.activityStatus = member.getActivityStatus().getStatusName();
        this.address = member.getAddresses().isEmpty() ? null : new AddressResponse(member.getAddresses().get(0));
        this.joinedDate = member.getCreatedAt();
        this.updatedTime = member.getUpdatedAt();
    }
}
