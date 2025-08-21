package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("google_builder")
public class GoogleOAuthLinkBuilder implements OAuthLinkBuilder {
    private final OAuth2ProviderProperties oAuth2ProviderProperties;

    public GoogleOAuthLinkBuilder(OAuth2ProviderListProperties oauth2ProviderListProperties) {
        oAuth2ProviderProperties = oauth2ProviderListProperties.getGoogle();
    }

    @Override
    public String buildUrl() {
        return oAuth2ProviderProperties.getBaseUrl() +
                "?client_id=" + oAuth2ProviderProperties.getClientId() +
                "&redirect_uri=" + oAuth2ProviderProperties.getRedirectUrl() +
                "&response_type=code" +
                "&scope=openid%20email%20profile";
    }
}
