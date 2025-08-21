package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("facebook_builder")
public class FacebookOAuthLinkBuilder implements OAuthLinkBuilder {
    private final OAuth2ProviderProperties oAuth2ProviderProperties;

    public FacebookOAuthLinkBuilder(OAuth2ProviderListProperties oauth2ProviderListProperties) {
        oAuth2ProviderProperties = oauth2ProviderListProperties.getFacebook();
    }

    @Override
    public String buildUrl() {
        return oAuth2ProviderProperties.getBaseUrl() +
                "?client_id=" + oAuth2ProviderProperties.getClientId() +
                "&redirect_uri=" + oAuth2ProviderProperties.getRedirectUrl() +
                "&response_type=code" +
                "&scope=public_profile,email";
    }
}