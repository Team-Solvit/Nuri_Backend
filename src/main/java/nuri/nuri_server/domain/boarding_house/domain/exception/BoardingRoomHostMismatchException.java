package nuri.nuri_server.domain.boarding_house.domain.exception;

import nuri.nuri_server.global.exception.ErrorType;
import nuri.nuri_server.global.exception.NuriBusinessException;
import org.springframework.http.HttpStatus;

public class BoardingRoomHostMismatchException extends NuriBusinessException {
    public BoardingRoomHostMismatchException() {
        super("요청한 유저와 게시물 작성 유저가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, ErrorType.BAD_REQUEST);
    }
}
