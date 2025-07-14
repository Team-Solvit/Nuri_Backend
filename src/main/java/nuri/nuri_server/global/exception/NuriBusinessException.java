package nuri.nuri_server.global.exception;

import org.springframework.http.HttpStatus;

public class NuriBusinessException extends NuriException {
    public NuriBusinessException(String message, HttpStatus status) {
        super(message, status);
    }
}
