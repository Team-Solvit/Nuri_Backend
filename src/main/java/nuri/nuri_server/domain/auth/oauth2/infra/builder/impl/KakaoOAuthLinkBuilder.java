package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_builder")
public class KakaoOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public KakaoOAuthLinkBuilder(OAuth2ProviderListProperties oauth2ProviderListProperties) {
        OAuth2ProviderProperties kakaoProps = oauth2ProviderListProperties.getKakao();
        this.clientId = kakaoProps.getClientId();
        this.redirectUri = kakaoProps.getRedirectUrl();
    }

    @Override
    public String buildUrl() {
        return "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&scope=profile_nickname,profile_image" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri;
    }
}
