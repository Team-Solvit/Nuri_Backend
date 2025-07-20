package nuri.nuri_server.domain.auth.oauth2.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("tiktok")
public class TiktokOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public TiktokOAuthLinkBuilder(OAuth2Properties oAuth2Properties) {
        OAuth2ProviderProperties tiktokProps = oAuth2Properties.getProviders().get("tiktok");
        this.clientId = tiktokProps.getClientId();
        this.redirectUri = tiktokProps.getRedirectUrl();
    }

    @Override
    public String buildUrl() {
        return "https://www.tiktok.com/v2/auth/authorize" +
                "?client_key=" + clientId +
                "&scope=user.info.basic" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri;
    }
}