package db.project.ecommerce.global.exception;

import db.project.ecommerce.global.dto.ErrorDto;
import db.project.ecommerce.global.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Illegal Argument")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorDto> handleCustomException(CustomException e, HttpServletRequest request) {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now().toString(),
                e.getErrorCode().getStatus(),
                e.getErrorCode().name(),
                e.getErrorCode().getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now().toString(),
                400,
                "INVALID_INPUT",
                errorMessage,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    // DB 프로시저/트리거에서 SIGNAL 로 던진 예외 처리.
    // 재고 부족 등은 JpaSystemException, DataIntegrityViolationException 등 다양한 형태로 오므로
    // 공통 부모인 DataAccessException 으로 받고, 예외 메시지 체인에서 트리거 메시지를 찾아 분기한다.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleDataAccessException(DataAccessException e, HttpServletRequest request) {
        String triggerMessage = findTriggerMessage(e);

        if (triggerMessage != null) {
            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(LocalDateTime.now().toString())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error("Out Of Stock")
                    .message(triggerMessage)
                    .path(request.getRequestURI())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Database Error")
                .message("처리 중 오류가 발생했습니다.")
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 예외 원인 체인을 따라가며 트리거에서 SIGNAL 한 "...재고가 부족..." 메시지를 찾아 반환한다.
    private String findTriggerMessage(Throwable e) {
        for (Throwable cause = e; cause != null; cause = cause.getCause()) {
            String message = cause.getMessage();
            if (message != null && message.contains("재고가 부족")) {
                // SQLException 의 message 는 SIGNAL 텍스트("상품 재고가 부족합니다.") 그대로인 경우가 많다.
                if (cause instanceof java.sql.SQLException) {
                    return message;
                }
            }
        }
        // SQLException 을 못 찾았더라도 메시지에 재고 부족 문구가 있으면 사용자용 기본 문구 반환
        for (Throwable cause = e; cause != null; cause = cause.getCause()) {
            String message = cause.getMessage();
            if (message != null && message.contains("재고가 부족")) {
                return "상품 재고가 부족합니다.";
            }
        }
        return null;
    }
}
