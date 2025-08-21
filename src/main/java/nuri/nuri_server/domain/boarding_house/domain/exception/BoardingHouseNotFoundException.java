package nuri.nuri_server.domain.boarding_house.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class BoardingHouseNotFoundException extends EntityNotFoundException {
    public BoardingHouseNotFoundException() {
        super("하숙집을 찾을 수 없습니다.");
    }
}
