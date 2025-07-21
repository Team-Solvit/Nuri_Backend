package nuri.nuri_server.domain.auth.oauth2.client.impl;

import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.global.feign.oauth2.KakaoOAuth2Client;
import nuri.nuri_server.global.feign.oauth2.req.KakaoTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.information.KakaoInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.KakaoTokenResponse;
import nuri.nuri_server.global.properties.OAuth2Properties;
import nuri.nuri_server.global.properties.OAuth2Properties.OAuth2ProviderProperties;
import org.springframework.stereotype.Component;

@Component("kakao_client")
public class KakaoOAuthClient implements OAuthClient {

    private final KakaoOAuth2Client kakaoOAuth2Client;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String grantType;

    public KakaoOAuthClient(OAuth2Properties oAuth2Properties, KakaoOAuth2Client kakaoOAuth2Client) {
        this.kakaoOAuth2Client = kakaoOAuth2Client;

        OAuth2ProviderProperties kakaoProps = oAuth2Properties.getTiktok();
        this.clientId = kakaoProps.getClientId();
        this.redirectUri = kakaoProps.getRedirectUrl();
        this.clientSecret = kakaoProps.getClientSecret();
        this.grantType = kakaoProps.getGrantType();
    }

    @Override
    public String getAccessToken(String code) {
        KakaoTokenRequest tokenRequest = KakaoTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .grantType(grantType)
                .build();

        KakaoTokenResponse tokenResponse = kakaoOAuth2Client.getAccessToken(tokenRequest.toMultiValueMap());

        return tokenResponse.access_token();
    }

    @Override
    public OAuth2InformationResponse getUserInfo(String accessToken) {
        KakaoInformationResponse response = kakaoOAuth2Client.getUserInformation(accessToken);
        KakaoInformationResponse.KakaoAccount kakaoAccount = response.getKakao_account();

        return OAuth2InformationResponse.builder()
                .id(String.valueOf(response.getId()))
                .name(kakaoAccount.getName())
                .profile(kakaoAccount.getProfile().getProfile_image_url())
                .build();
    }
}
