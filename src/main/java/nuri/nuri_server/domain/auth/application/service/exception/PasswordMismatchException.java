package nuri.nuri_server.domain.auth.application.service.exception;

import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends NuriBusinessException {
    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
