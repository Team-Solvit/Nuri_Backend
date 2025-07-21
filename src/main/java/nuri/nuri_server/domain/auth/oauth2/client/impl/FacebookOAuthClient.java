package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.FacebookOAuth2Client;
import nuri.nuri_server.global.feign.oauth2.req.FacebookTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.FacebookInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.FacebookTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2Properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("facebook_client")
public class FacebookOAuthClient implements OAuthClient {

    private final FacebookOAuth2Client facebookOAuth2Client;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public FacebookOAuthClient(OAuth2Properties oAuth2Properties, FacebookOAuth2Client facebookOAuth2Client) {
        this.facebookOAuth2Client = facebookOAuth2Client;

        OAuth2ProviderProperties facebookProps = oAuth2Properties.getFacebook();
        this.clientId = facebookProps.getClientId();
        this.redirectUri = facebookProps.getRedirectUrl();
        this.clientSecret = facebookProps.getClientSecret();
    }

    @Override
    public String getAccessToken(String code) {
        FacebookTokenRequest tokenRequest = FacebookTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build();

        FacebookTokenResponse tokenResponse = facebookOAuth2Client.getAccessToken(
                tokenRequest.getClientId(),
                tokenRequest.getRedirectUri(),
                tokenRequest.getClientSecret(),
                code
        );

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        FacebookInformationResponse response = facebookOAuth2Client.getUserInformation(accessToken);
        return OAuth2InformationResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .profile(response.getPicture().getData().getUrl())
                .build();
    }
}
