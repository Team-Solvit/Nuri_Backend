package nuri.nuri_server.domain.boarding_manage.domain.exception;

import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class ThirdPartyMismatchException extends NuriBusinessException {
    public ThirdPartyMismatchException() {
        super("담당 제3자와 일치하지 않습니다.", HttpStatus.BAD_REQUEST, ErrorType.BAD_REQUEST);
    }
}
