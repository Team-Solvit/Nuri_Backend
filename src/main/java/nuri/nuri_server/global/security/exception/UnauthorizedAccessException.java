package nuri.nuri_server.global.security.exception;

import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends NuriBusinessException {
    public UnauthorizedAccessException() {
        super("인증 정보가 없거나 접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED);
    }
}
