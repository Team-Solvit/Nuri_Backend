package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_builder")
public class KakaoOAuthLinkBuilder implements OAuthLinkBuilder {
    private final OAuth2ProviderProperties oAuth2ProviderProperties;

    public KakaoOAuthLinkBuilder(OAuth2ProviderListProperties oauth2ProviderListProperties) {
        oAuth2ProviderProperties = oauth2ProviderListProperties.getKakao();
    }

    @Override
    public String buildUrl() {
        return oAuth2ProviderProperties.getBaseUrl() +
                "?client_id=" + oAuth2ProviderProperties.getClientId() +
                "&scope=profile_nickname,profile_image" +
                "&response_type=code" +
                "&redirect_uri=" + oAuth2ProviderProperties.getRedirectUrl();
    }
}
