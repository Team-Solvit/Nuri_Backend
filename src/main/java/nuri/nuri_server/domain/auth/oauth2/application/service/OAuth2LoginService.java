package nuri.nuri_server.domain.auth.oauth2.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.auth.oauth2.infra.client.OAuthClient;
import nuri.nuri_server.domain.auth.oauth2.infra.client.dto.OAuth2InformationResponse;
import nuri.nuri_server.domain.auth.oauth2.domain.entity.OAuthSignUpTempUser;
import nuri.nuri_server.domain.auth.oauth2.domain.repository.OAuthSignUpTempUserRepository;
import nuri.nuri_server.domain.auth.oauth2.application.service.dto.OAuthLoginValue;
import nuri.nuri_server.domain.auth.oauth2.application.service.exception.OAuthProviderNotFoundException;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.domain.user.domain.repository.UserRepository;
import nuri.nuri_server.global.security.jwt.CookieManager;
import nuri.nuri_server.global.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginService {

    private final Map<String, OAuthClient> oauth2ClientMap;
    private final UserRepository userRepository;
    private final OAuthSignUpTempUserRepository oauthSignUpTempUserRepository;
    private final JwtProvider jwtProvider;

    @Value("${oauth2.new-user.caching-time}")
    private Long cachingTime;
    @Value("${oauth2.new-user.front-redirect-url}")
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
        TokenResponse tokenResponse = jwtProvider.createTokenResponse(user);

        log.info("사용자 {}님이 로그인 하셨습니다.", user.getUserId());

        return OAuthLoginValue.builder()
                .accessToken(tokenResponse.accessToken())
                .refreshToken(tokenResponse.refreshTokenCookie())
                .isNewUser(false)
                .build();
    }

    private OAuthLoginValue createRedirectUri(OAuth2InformationResponse userInfo, String provider) {
        String oauthId = cachingUserInfo(userInfo, provider);
        return OAuthLoginValue.builder()
                .isNewUser(true)
                .redirectUrl(redirectUri + "?oauth-id=" + oauthId)
                .build();
    }

    private String cachingUserInfo(OAuth2InformationResponse userInfo, String provider) {
        String oauthId = provider + "_" + userInfo.id();
        OAuthSignUpTempUser oauthSignUpTempUser = OAuthSignUpTempUser.builder()
                .OAuthId(oauthId)
                .name(userInfo.name())
                .profile(userInfo.profile())
                .id(userInfo.id())
                .provider(provider)
                .timeToLive(cachingTime)
                .build();

        oauthSignUpTempUserRepository.save(oauthSignUpTempUser);

        return oauthId;
    }

    private OAuthClient selectOAuth2Client(String provider) {
        OAuthClient client = oauth2ClientMap.get(provider.toLowerCase() + "_client");
        if (client == null) {
            throw new OAuthProviderNotFoundException(provider);
        }
        return client;
    }
}
