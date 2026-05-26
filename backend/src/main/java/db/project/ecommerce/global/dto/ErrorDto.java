package db.project.ecommerce.global.dto;

public record ErrorDto (
        String timestamp,
        int status,
        String errorCode,
        String message,
        String path

) {}
