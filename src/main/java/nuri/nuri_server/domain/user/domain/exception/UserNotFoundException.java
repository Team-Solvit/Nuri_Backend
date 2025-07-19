package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String username) {
        super("유저 아이디가 " + username + "인 유저가 존재하지 않습니다.");
    }
}
