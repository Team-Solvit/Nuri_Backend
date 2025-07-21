package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.GoogleOAuth2Client;
import nuri.nuri_server.global.feign.oauth2.req.GoogleTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.GoogleInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.GoogleTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2Properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("google_client")
public class GoogleOAuthClient implements OAuthClient {

    private final GoogleOAuth2Client googleOAuth2Client;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public GoogleOAuthClient(OAuth2Properties oAuth2Properties, GoogleOAuth2Client googleOAuth2Client) {
        this.googleOAuth2Client = googleOAuth2Client;

        OAuth2ProviderProperties googleProps = oAuth2Properties.getGoogle();
        this.clientId = googleProps.getClientId();
        this.redirectUri = googleProps.getRedirectUrl();
        this.clientSecret = googleProps.getClientSecret();
        this.grantType = googleProps.getGrantType();
    }

    @Override
    public String getAccessToken(String code) {
        GoogleTokenRequest tokenRequest = GoogleTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType(grantType)
                .build();

        GoogleTokenResponse tokenResponse = googleOAuth2Client.getAccessToken(tokenRequest.toMultiValueMap());

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        GoogleInformationResponse response = googleOAuth2Client.getUserInformation(accessToken);
        return OAuth2InformationResponse.builder()
                .id(response.id())
                .name(response.name())
                .profile(response.picture())
                .build();
    }
}
