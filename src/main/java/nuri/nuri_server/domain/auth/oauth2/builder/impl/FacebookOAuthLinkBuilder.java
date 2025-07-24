package nuri.nuri_server.domain.auth.oauth2.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("facebook_builder")
public class FacebookOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public FacebookOAuthLinkBuilder(OAuth2Properties oAuth2Properties) {
        OAuth2ProviderProperties facebookProps = oAuth2Properties.getFacebook();
        this.clientId = facebookProps.getClientId();
        this.redirectUri = facebookProps.getRedirectUrl();
    }

    @Override
    public String buildUrl() {
        return "https://www.facebook.com/v23.0/dialog/oauth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=public_profile,email";
    }
}