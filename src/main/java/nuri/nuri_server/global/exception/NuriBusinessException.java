package nuri.nuri_server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NuriBusinessException extends NuriException {
    private final ErrorType errorType;

    public NuriBusinessException(String message, HttpStatus status, ErrorType errorType) {
        super(message, status);
        this.errorType = errorType;
    }
}
