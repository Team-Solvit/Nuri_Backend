package nuri.nuri_server.domain.auth.oauth2.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_builder")
public class KakaoOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public KakaoOAuthLinkBuilder(OAuth2Properties oAuth2Properties) {
        OAuth2ProviderProperties kakaoProps = oAuth2Properties.getKakao();
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
