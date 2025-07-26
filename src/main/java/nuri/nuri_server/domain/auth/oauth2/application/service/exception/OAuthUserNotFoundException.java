package nuri.nuri_server.domain.auth.oauth2.application.service.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class OAuthUserNotFoundException extends EntityNotFoundException {
    public OAuthUserNotFoundException(String oauthId) {
        super("oauthId가 " + oauthId + "인 유저가 존재하지 않습니다.");
    }
}