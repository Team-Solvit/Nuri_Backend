package nuri.nuri_server.domain.notification.domain.exception;

import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class NotificationUserMismatchException extends NuriBusinessException {
    public NotificationUserMismatchException() {
        super("요청한 유저와 알림을 받은 유저가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, ErrorType.BAD_REQUEST);
    }
}
