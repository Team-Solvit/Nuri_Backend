package nuri.nuri_server.global.feign.google_map.exception;

import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.http.HttpStatus;

public class GoogleMapsException extends NuriSystemError {
    public GoogleMapsException(String message) {
        super(message, HttpStatus.BAD_GATEWAY);
    }
}
