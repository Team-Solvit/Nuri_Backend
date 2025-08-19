package nuri.nuri_server.domain.chat.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class UserRoomNotFoundException extends EntityNotFoundException {
    public UserRoomNotFoundException(String roomId, String userId) {
        super("방 아이디가 " + roomId + ", 유저 아이디가 " + userId + "인 관계가 존재하지 않습니다.");
    }
}