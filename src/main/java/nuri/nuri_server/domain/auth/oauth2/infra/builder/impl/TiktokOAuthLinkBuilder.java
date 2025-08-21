package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("tiktok_builder")
public class TiktokOAuthLinkBuilder implements OAuthLinkBuilder {
    private final OAuth2ProviderProperties oAuth2ProviderProperties;

    public TiktokOAuthLinkBuilder(OAuth2ProviderListProperties oauth2ProviderListProperties) {
        oAuth2ProviderProperties = oauth2ProviderListProperties.getTiktok();
    }

    @Override
    public String buildUrl() {
        return oAuth2ProviderProperties.getBaseUrl() +
                "?client_key=" + oAuth2ProviderProperties.getClientId() +
                "&scope=user.info.basic" +
                "&response_type=code" +
                "&redirect_uri=" + oAuth2ProviderProperties.getRedirectUrl();
    }
}