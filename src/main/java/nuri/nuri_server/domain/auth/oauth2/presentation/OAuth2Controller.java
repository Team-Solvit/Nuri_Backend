package nuri.nuri_server.domain.auth.oauth2.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthLoginRequest;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthSignUpRequest;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.res.OAuthLoginResponse;
import nuri.nuri_server.domain.auth.oauth2.service.OAuth2SignUpService;
import nuri.nuri_server.domain.auth.oauth2.service.dto.OAuthLoginValue;
import nuri.nuri_server.domain.auth.oauth2.service.OAuth2LinkService;
import nuri.nuri_server.domain.auth.oauth2.service.OAuth2LoginService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2LinkService oAuth2LinkService;
    private final OAuth2LoginService oAuth2LoginService;
    private final OAuth2SignUpService oAuth2SignUpService;
    private final HttpServletResponse response;

    @QueryMapping
    public String getOAuth2Link(@Argument String provider) {
        return oAuth2LinkService.execute(provider);
    }

    @MutationMapping
    public OAuthLoginResponse loginByOAuthCode(@Argument("OAuthLoginInput") @Valid OAuthLoginRequest loginRequest) {
        String code = loginRequest.code();
        String provider = loginRequest.provider();

        String oAuth2AccessToken = oAuth2LoginService.getAccessToken(code, provider);
        OAuthLoginValue oAuthLoginValue = oAuth2LoginService.createTokenByOAuth2Token(oAuth2AccessToken, provider);

        response.setHeader(HttpHeaders.AUTHORIZATION, oAuthLoginValue.getAccessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, oAuthLoginValue.getRefreshToken());

        return OAuthLoginResponse.from(oAuthLoginValue);
    }

    @MutationMapping
    public String saveOAuthUserInfo(@Argument("saveUserInput") @Valid OAuthSignUpRequest oAuthSignUpRequest) {
        TokenResponse tokenResponse = oAuth2SignUpService.signUp(oAuthSignUpRequest);

        response.setHeader(HttpHeaders.AUTHORIZATION, tokenResponse.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, tokenResponse.refreshTokenCookie());

        return "OAuth 로그인 유저 회원가입에 성공하였습니다.";
    }
}
