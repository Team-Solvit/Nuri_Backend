package nuri.nuri_server.domain.auth.oauth2.infra.client.impl;

import nuri.nuri_server.domain.auth.oauth2.infra.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.infra.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.KakaoOAuth2TokenClient;
import nuri.nuri_server.global.feign.oauth2.KakaoOAuth2UserInfoClient;
import nuri.nuri_server.global.feign.oauth2.req.KakaoTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.KakaoInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.information.KakaoProperties;
import nuri.nuri_server.global.feign.oauth2.res.token.KakaoTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_client")
public class KakaoOAuthClient implements OAuthClient {

    private final KakaoOAuth2TokenClient kakaoOAuth2TokenClient;
    private final KakaoOAuth2UserInfoClient kakaoOAuth2UserInfoClient;
    private final OAuth2ProviderProperties kakaoProps;

    public KakaoOAuthClient(OAuth2Properties oAuth2Properties, KakaoOAuth2TokenClient kakaoOAuth2TokenClient, KakaoOAuth2UserInfoClient kakaoOAuth2UserInfoClient) {
        this.kakaoOAuth2TokenClient = kakaoOAuth2TokenClient;
        this.kakaoOAuth2UserInfoClient = kakaoOAuth2UserInfoClient;
        kakaoProps = oAuth2Properties.getKakao();
    }

    @Override
    public String getAccessToken(String code) {
        KakaoTokenRequest tokenRequest = KakaoTokenRequest.builder()
                .code(code)
                .clientId(kakaoProps.getClientId())
                .clientSecret(kakaoProps.getClientSecret())
                .redirectUri(kakaoProps.getRedirectUrl())
                .grantType(kakaoProps.getGrantType())
                .build();

        KakaoTokenResponse tokenResponse = kakaoOAuth2TokenClient.getAccessToken(tokenRequest);

        return tokenResponse.accessToken();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        KakaoInformationResponse response = kakaoOAuth2UserInfoClient.getUserInformation("Bearer " + accessToken);
        KakaoProperties kakaoProperties = response.properties();

        return OAuth2InformationResponse.builder()
                .id(String.valueOf(response.id()))
                .name(kakaoProperties.nickname())
                .profile(kakaoProperties.profileImage())
                .build();
    }
}
