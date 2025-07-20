package nuri.nuri_server.domain.auth.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.application.service.AuthService;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.auth.oauth2.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthTempUser;
import nuri.nuri_server.domain.auth.oauth2.domain.repository.OAuthTempUserRepository;
import nuri.nuri_server.domain.auth.oauth2.service.dto.OAuthLoginValue;
import nuri.nuri_server.domain.auth.oauth2.service.exception.OAuthProviderNotFoundException;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.global.security.jwt.CookieManager;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static nuri.nuri_server.domain.auth.local.application.service.AuthService.createTokenResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginService {

    private final Map<String, OAuthClient> oAuth2ClientMap;
    private final UserRepository userRepository;
    private final OAuthTempUserRepository oAuthTempUserRepository;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;

    @Value("${oauth2.new-user.caching-time}")
    private Long cachingTime;
    @Value("${oauth2.new-user.front-redirect-url-time}")
    private String redirectUri;

    public String getAccessToken(String code, String provider) {
        OAuthClient client = selectOAuth2Client(provider);
        return client.getAccessToken(code);
    }

    public OAuthLoginValue createTokenByOAuth2Token(String accessToken, String provider) {
        OAuthClient client = selectOAuth2Client(provider);
        OAuth2InformationResponse userInfo = client.getUserInfo(accessToken);

        return userRepository.findByOauthProviderAndOauthId(provider, userInfo.id())
                .map(this::createLoginResponse)
                .orElseGet(() -> createRedirectUri(userInfo, provider));
    }

    private OAuthLoginValue createLoginResponse(UserEntity user) {
        TokenResponse tokenResponse = createTokenResponse(user, jwtProvider, log, cookieManager);

        return OAuthLoginValue.builder()
                .accessToken(tokenResponse.accessToken())
                .refreshToken(tokenResponse.refreshTokenCookie())
                .isNewUser(false)
                .build();
    }

    private OAuthLoginValue createRedirectUri(OAuth2InformationResponse userInfo, String provider) {
        String oAuthId = cachingUserInfo(userInfo, provider);
        return OAuthLoginValue.builder()
                .isNewUser(true)
                .redirectUrl(redirectUri + "?oauth-id=" + oAuthId)
                .build();
    }

    private String cachingUserInfo(OAuth2InformationResponse userInfo, String provider) {
        String oAuthId = provider + userInfo.id();
        OAuthTempUser oAuthTempUser = OAuthTempUser.builder()
                .OAuthId(oAuthId)
                .name(userInfo.name())
                .profile(userInfo.profile())
                .id(userInfo.id())
                .provider(provider)
                .timeToLive(cachingTime)
                .build();

        oAuthTempUserRepository.save(oAuthTempUser);

        return oAuthId;
    }

    private OAuthClient selectOAuth2Client(String provider) {
        OAuthClient client = oAuth2ClientMap.get(provider.toLowerCase());
        if (client == null) {
            throw new OAuthProviderNotFoundException(provider);
        }
        return client;
    }
}
