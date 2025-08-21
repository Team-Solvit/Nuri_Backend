package nuri.nuri_server.domain.auth.oauth2.infra.client.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.infra.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.GoogleOAuth2TokenClient;
import nuri.nuri_server.global.feign.oauth2.GoogleOAuth2UserInfoClient;
import nuri.nuri_server.global.feign.oauth2.req.GoogleTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.GoogleInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.GoogleTokenResponse;
import nuri.nuri_server.global.properties.OAuth2ProviderListProperties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component("google_client")
public class GoogleOAuthClient implements OAuthClient {

    private final GoogleOAuth2TokenClient googleOAuth2TokenClient;
    private final GoogleOAuth2UserInfoClient googleOAuth2UserInfoClient;
    private final OAuth2ProviderProperties googleProps;

    public GoogleOAuthClient(OAuth2ProviderListProperties oauth2ProviderListProperties, GoogleOAuth2TokenClient googleOAuth2TokenClient, GoogleOAuth2UserInfoClient googleOAuth2UserInfoClient) {
        this.googleOAuth2TokenClient = googleOAuth2TokenClient;
        this.googleOAuth2UserInfoClient = googleOAuth2UserInfoClient;
        googleProps = oauth2ProviderListProperties.getGoogle();
    }

    @Override
    public String getAccessToken(String code) {
        String rawCode  = URLDecoder.decode(code, StandardCharsets.UTF_8);

        GoogleTokenRequest tokenRequest = GoogleTokenRequest.builder()
                .code(rawCode)
                .clientId(googleProps.getClientId())
                .clientSecret(googleProps.getClientSecret())
                .redirectUri(googleProps.getRedirectUrl())
                .grantType(googleProps.getGrantType())
                .build();

        GoogleTokenResponse tokenResponse = googleOAuth2TokenClient.getAccessToken(tokenRequest);

        return tokenResponse.accessToken();
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
