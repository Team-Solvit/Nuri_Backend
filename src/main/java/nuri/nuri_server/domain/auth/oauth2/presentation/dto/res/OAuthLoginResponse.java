package nuri.nuri_server.domain.auth.oauth2.presentation.dto.res;

import nuri.nuri_server.domain.auth.oauth2.application.service.dto.OAuthLoginValue;

public record OAuthLoginResponse(
        String oauthId,
        Boolean isNewUser
) {
    public static OAuthLoginResponse from(OAuthLoginValue value) {
        return new OAuthLoginResponse(value.getOauthId(), value.isNewUser());
    }
}
