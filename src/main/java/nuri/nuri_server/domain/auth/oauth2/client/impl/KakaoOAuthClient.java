package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.KakaoOAuth2TokenClient;
import nuri.nuri_server.global.feign.oauth2.KakaoOAuth2UserInfoClient;
import nuri.nuri_server.global.feign.oauth2.req.KakaoTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.KakaoInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.KakaoTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_client")
public class KakaoOAuthClient implements OAuthClient {

    private final KakaoOAuth2TokenClient kakaoOAuth2TokenClient;
    private final KakaoOAuth2UserInfoClient kakaoOAuth2UserInfoClient;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public KakaoOAuthClient(OAuth2Properties oAuth2Properties, KakaoOAuth2TokenClient kakaoOAuth2TokenClient, KakaoOAuth2UserInfoClient kakaoOAuth2UserInfoClient) {
        this.kakaoOAuth2TokenClient = kakaoOAuth2TokenClient;
        this.kakaoOAuth2UserInfoClient = kakaoOAuth2UserInfoClient;

        OAuth2ProviderProperties kakaoProps = oAuth2Properties.getKakao();
        this.clientId = kakaoProps.getClientId();
        this.redirectUri = kakaoProps.getRedirectUrl();
        this.clientSecret = kakaoProps.getClientSecret();
        this.grantType = kakaoProps.getGrantType();
    }

    @Override
    public String getAccessToken(String code) {
        KakaoTokenRequest tokenRequest = KakaoTokenRequest.builder()
                .code(code)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .grant_type(grantType)
                .build();

        KakaoTokenResponse tokenResponse = kakaoOAuth2TokenClient.getAccessToken(tokenRequest);

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        KakaoInformationResponse response = kakaoOAuth2UserInfoClient.getUserInformation("Bearer " + accessToken);
        KakaoInformationResponse.KakaoProperties kakaoProperties = response.getProperties();

        return OAuth2InformationResponse.builder()
                .id(String.valueOf(response.getId()))
                .name(kakaoProperties.getNickname())
                .profile(kakaoProperties.getProfile_image())
                .build();
    }
}
