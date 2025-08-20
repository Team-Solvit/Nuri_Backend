package nuri.nuri_server.domain.auth.oauth2.presentation.dto.res;

import nuri.nuri_server.domain.auth.oauth2.application.service.dto.OAuthLoginValue;

public record OAuthLoginResponseDto(
        String oauthId,
        Boolean isNewUser
) {
    public static OAuthLoginResponseDto from(OAuthLoginValue value) {
        return new OAuthLoginResponseDto(value.getOauthId(), value.isNewUser());
    }
}
