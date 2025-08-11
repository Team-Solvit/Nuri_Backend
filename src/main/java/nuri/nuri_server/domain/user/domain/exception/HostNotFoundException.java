package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class HostNotFoundException extends EntityNotFoundException {
    public HostNotFoundException(String HostId) {
        super("호스트 아이디가 " + HostId + "인 호스트가 존재하지 않습니다.");
    }
}
