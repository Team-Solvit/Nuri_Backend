package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.TiktokOAuth2Client;
import nuri.nuri_server.global.feign.oauth2.req.TiktokTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.TiktokInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.TiktokTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("tiktok_client")
public class TiktokOAuthClient implements OAuthClient {

    private final TiktokOAuth2Client tiktokOAuth2Client;

    private final String clientKey;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public TiktokOAuthClient(OAuth2Properties oAuth2Properties, TiktokOAuth2Client tiktokOAuth2Client) {
        this.tiktokOAuth2Client = tiktokOAuth2Client;

        OAuth2ProviderProperties tiktokProps = oAuth2Properties.getTiktok();
        this.clientKey = tiktokProps.getClientId();
        this.redirectUri = tiktokProps.getRedirectUrl();
        this.clientSecret = tiktokProps.getClientSecret();
        this.grantType = tiktokProps.getGrantType();
    }

    @Override
    public String getAccessToken(String code) {
        TiktokTokenRequest tokenRequest = TiktokTokenRequest.builder()
                .code(code)
                .clientKey(clientKey)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType(grantType)
                .build();
        TiktokTokenResponse tokenResponse = tiktokOAuth2Client.getAccessToken(tokenRequest.toMultiValueMap());

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        TiktokInformationResponse response = tiktokOAuth2Client.getUserInformation(accessToken);
        TiktokInformationResponse.User user = response.getData().getUser();

        return OAuth2InformationResponse.builder()
                .id(user.getOpen_id())
                .name(user.getDisplay_name())
                .profile(user.getAvatar_url())
                .build();
    }
}
