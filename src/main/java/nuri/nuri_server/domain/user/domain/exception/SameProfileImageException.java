package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class SameProfileImageException extends NuriBusinessException {
    public SameProfileImageException() {
        super("이미 존재하는 프로필 이미지 입니다.", HttpStatus.BAD_REQUEST, ErrorType.BAD_REQUEST);
    }
}
