package nuri.nuri_server.global.feign.exception;

import nuri.nuri_server.global.exception.NuriSystemError;
import org.springframework.http.HttpStatus;

public class FeignClientResponseException extends NuriSystemError {
    public FeignClientResponseException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
