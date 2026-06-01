package db.project.ecommerce.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeleteMemberResponse {
    private final String id;
    private final String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private final LocalDateTime deletedDate;

    private final String message;

    public DeleteMemberResponse(String loginId, LocalDateTime deletedDate) {
        this.id = loginId;
        this.status = "탈퇴";
        this.deletedDate = deletedDate;
        this.message = "회원 정보 삭제가 성공적으로 완료되었습니다";
    }
}
