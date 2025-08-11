package nuri.nuri_server.global.exception.stomp;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class StompErrorResponse {
    private final String message;
    private final OffsetDateTime timestamp;

    @Builder
    public StompErrorResponse(String message) {
        this.message = message;
        this.timestamp = OffsetDateTime.now();
    }
}
