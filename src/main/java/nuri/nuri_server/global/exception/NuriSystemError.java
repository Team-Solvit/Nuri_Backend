package nuri.nuri_server.global.exception;

import org.springframework.http.HttpStatus;

public class NuriSystemError extends NuriException {
    public NuriSystemError(String message, HttpStatus status) {
        super(message, status);
    }
}
