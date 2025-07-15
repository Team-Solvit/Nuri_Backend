package nuri.nuri_server.global.entity.exception;

import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends NuriBusinessException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
