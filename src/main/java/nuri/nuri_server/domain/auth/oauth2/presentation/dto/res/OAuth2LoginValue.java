package nuri.nuri_server.domain.auth.oauth2.presentation.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuth2LoginValue {
    private String accessToken;
    private String refreshToken;
    private String oauthId;
    private boolean isNewUser;
}