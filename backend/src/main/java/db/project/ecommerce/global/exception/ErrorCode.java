package db.project.ecommerce.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND (404, "요청하신 내용을 찾을 수 없습니다."),
    FORBIDDEN (403, "접근 권한이 없습니다"),
    BAD_REQUEST (400, "잘못된 요청입니다.");

    private final int status;
    private final String message;
}
