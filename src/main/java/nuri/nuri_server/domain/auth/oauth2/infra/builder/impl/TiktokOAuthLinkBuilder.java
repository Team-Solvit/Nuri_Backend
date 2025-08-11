package nuri.nuri_server.domain.auth.oauth2.infra.builder.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.builder.OAuthLinkBuilder;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("tiktok_builder")
public class TiktokOAuthLinkBuilder implements OAuthLinkBuilder {
    private final String clientId;
    private final String redirectUri;

    public TiktokOAuthLinkBuilder(OAuth2Properties oauth2Properties) {
        OAuth2ProviderProperties tiktokProps = oauth2Properties.getTiktok();
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