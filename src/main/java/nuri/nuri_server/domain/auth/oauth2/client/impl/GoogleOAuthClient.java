package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.GoogleOAuth2TokenClient;
import nuri.nuri_server.global.feign.oauth2.GoogleOAuth2UserInfoClient;
import nuri.nuri_server.global.feign.oauth2.req.GoogleTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.GoogleInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.GoogleTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2Properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component("google_client")
public class GoogleOAuthClient implements OAuthClient {

    private final GoogleOAuth2TokenClient googleOAuth2TokenClient;
    private final GoogleOAuth2UserInfoClient googleOAuth2UserInfoClient;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public GoogleOAuthClient(OAuth2Properties oAuth2Properties, GoogleOAuth2TokenClient googleOAuth2TokenClient, GoogleOAuth2UserInfoClient googleOAuth2UserInfoClient) {
        this.googleOAuth2TokenClient = googleOAuth2TokenClient;
        this.googleOAuth2UserInfoClient = googleOAuth2UserInfoClient;

        OAuth2ProviderProperties googleProps = oAuth2Properties.getGoogle();
        this.clientId = googleProps.getClientId();
        this.redirectUri = googleProps.getRedirectUrl();
        this.clientSecret = googleProps.getClientSecret();
        this.grantType = googleProps.getGrantType();
    }

    @Override
    public String getAccessToken(String code) {
        String rawCode  = URLDecoder.decode(code, StandardCharsets.UTF_8);

        GoogleTokenRequest tokenRequest = GoogleTokenRequest.builder()
                .code(rawCode)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .grant_type(grantType)
                .build();

        GoogleTokenResponse tokenResponse = googleOAuth2TokenClient.getAccessToken(tokenRequest);

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        GoogleInformationResponse response = googleOAuth2UserInfoClient.getUserInformation("Bearer " + accessToken);
        return OAuth2InformationResponse.builder()
                .id(response.sub())
                .name(response.name())
                .profile(response.picture())
                .build();
    }
}
