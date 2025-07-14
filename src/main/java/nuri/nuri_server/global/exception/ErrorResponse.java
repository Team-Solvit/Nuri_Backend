package nuri.nuri_server.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    String code;
    String message;
    LocalDateTime timestamp;
}
