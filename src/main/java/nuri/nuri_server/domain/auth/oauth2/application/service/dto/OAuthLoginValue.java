package nuri.nuri_server.domain.auth.oauth2.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuthLoginValue {
    private String accessToken;
    private String refreshToken;
    private String redirectUrl;
    private boolean isNewUser;
}