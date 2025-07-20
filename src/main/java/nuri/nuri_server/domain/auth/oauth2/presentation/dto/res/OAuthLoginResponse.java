package nuri.nuri_server.domain.auth.oauth2.presentation.dto.res;

import nuri.nuri_server.domain.auth.oauth2.service.dto.OAuthLoginValue;

public record OAuthLoginResponse(
        String redirectUrl,
        Boolean isNewUser
) {
    public static OAuthLoginResponse from(OAuthLoginValue value) {
        return new OAuthLoginResponse(value.getRedirectUrl(), value.isNewUser());
    }
}
