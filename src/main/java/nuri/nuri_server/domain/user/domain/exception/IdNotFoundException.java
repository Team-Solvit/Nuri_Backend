package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.exception.entity.EntityNotFoundException;

public class IdNotFoundException extends EntityNotFoundException {
    public IdNotFoundException(String id) {
        super("유저 id가 " + id + " 인 유저가 존재하지 않습니다.");
    }
}
