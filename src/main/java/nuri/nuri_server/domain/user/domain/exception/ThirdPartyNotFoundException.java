package nuri.nuri_server.domain.user.domain.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

import java.util.UUID;

public class ThirdPartyNotFoundException extends EntityNotFoundException {
    public ThirdPartyNotFoundException(UUID thirdPartyId) {
        super("제3자 아이디가 " + thirdPartyId + "인 제3자가 존재하지 않습니다.");
    }
}
