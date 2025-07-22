package nuri.nuri_server.domain.chat.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class RoomNotFoundException extends EntityNotFoundException {
    public RoomNotFoundException(String roomId) {
        super("방 아이디가 " + roomId + "인 방이 존재하지 않습니다.");
    }
}
