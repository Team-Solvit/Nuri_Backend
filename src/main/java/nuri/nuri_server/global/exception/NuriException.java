package nuri.nuri_server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NuriException extends RuntimeException {

    private final HttpStatus status;

    public NuriException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
