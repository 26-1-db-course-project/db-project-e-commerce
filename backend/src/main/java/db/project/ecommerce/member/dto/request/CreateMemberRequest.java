package db.project.ecommerce.member.dto.request;

import db.project.ecommerce.member.domain.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String id;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotNull(message = "역할은 필수입니다.")
    private MemberRole role;

    @NotNull(message = "멤버 등급은 필수입니다.")
    private MemberType memberType;

    @NotNull(message = "주소는 필수입니다.")
    private AddressRequest address;

    public Member toEntity(MemberGrade grade, ActivityStatus activityStatus) {
        return Member.builder()
                .loginId(id)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .grade(grade)
                .activityStatus(activityStatus)
                .build();
    }
}
