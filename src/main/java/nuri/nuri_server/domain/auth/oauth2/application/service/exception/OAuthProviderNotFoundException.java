package nuri.nuri_server.domain.auth.oauth2.application.service.exception;

import nuri.nuri_server.global.entity.exception.EntityNotFoundException;

public class OAuthProviderNotFoundException extends EntityNotFoundException {
    public OAuthProviderNotFoundException(String provider) {
        super("OAuth 서비스 이름이 " + provider + "인 OAuth 서비스가 존재하지 않습니다.");
    }
}
