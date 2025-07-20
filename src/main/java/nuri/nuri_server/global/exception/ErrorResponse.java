package nuri.nuri_server.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String code;
    private final OffsetDateTime timestamp;

    @Builder
    public ErrorResponse(String code) {
        this.code = code;
        this.timestamp = OffsetDateTime.now();
    }

    public Map<String, Object> getMap() {
        return Map.of(
                "code", this.getCode(),
                "timestamp", this.getTimestamp().toString()
        );
    }
}
