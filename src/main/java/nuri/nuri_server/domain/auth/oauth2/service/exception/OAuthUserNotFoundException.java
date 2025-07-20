package nuri.nuri_server.domain.auth.oauth2.service.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class OAuthUserNotFoundException extends EntityNotFoundException {
    public OAuthUserNotFoundException(String oAuthId) {
        super("oAuthId가 " + oAuthId + "인 유저가 존재하지 않습니다.");
    }
}