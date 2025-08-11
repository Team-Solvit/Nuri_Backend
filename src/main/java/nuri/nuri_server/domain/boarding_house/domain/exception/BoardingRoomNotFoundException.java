package nuri.nuri_server.domain.boarding_house.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class BoardingRoomNotFoundException extends EntityNotFoundException {
    public BoardingRoomNotFoundException() {
      super("하숙방을 찾을 수 없습니다.");
    }
}
