package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("google_builder")
public class GoogleOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public GoogleOAuthLinkBuilder(OAuth2Properties oauth2Properties) {
        OAuth2ProviderProperties googleProps = oauth2Properties.getGoogle();
        this.clientId = googleProps.getClientId();
        this.redirectUri = googleProps.getRedirectUrl();
    }

    @Override
    public String buildUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=openid%20email%20profile";
    }
}
