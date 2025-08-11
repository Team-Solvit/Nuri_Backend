package nuri.nuri_server.global.security.exception;

import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class SecurityAccessDeniedException extends NuriBusinessException {
    public SecurityAccessDeniedException() {
      super("권한으로 인한 인증 오류가 발생했습니다.", HttpStatus.FORBIDDEN, ErrorType.FORBIDDEN);
    }
}
