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
import nuri.nuri_server.global.util.OAuthStateUtil;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Controller
@RequiredArgsConstructor
@Validated
public class OAuth2Controller {

    private final OAuth2LinkService oAuth2LinkService;
    private final OAuth2LoginService oAuth2LoginService;
    private final OAuth2SignUpService oAuth2SignUpService;

    @QueryMapping
    public String getOAuth2Link(@Argument String provider, HttpServletResponse response) {
        String url = oAuth2LinkService.execute(provider);
        String state = OAuthStateUtil.generateState();

        ResponseCookie stateCookie = ResponseCookie.from("state", state)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, stateCookie.toString());
        return oAuth2LinkService.addStateUrl(url, state);
    }

    @MutationMapping
    public OAuthLoginResponse loginByOAuthCode(@Argument @Valid OAuthLoginRequest loginRequest, HttpServletResponse response) {
        String code = loginRequest.code();
        String provider = loginRequest.provider();

        String oAuth2AccessToken = oAuth2LoginService.getAccessToken(code, provider);
        OAuthLoginValue oAuthLoginValue = oAuth2LoginService.createTokenByOAuth2Token(oAuth2AccessToken, provider);

        response.setHeader(HttpHeaders.AUTHORIZATION, oAuthLoginValue.getAccessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, oAuthLoginValue.getRefreshToken());

        return OAuthLoginResponse.from(oAuthLoginValue);
    }

    @MutationMapping
    public String OAuth2SignUp(@Argument @Valid OAuthSignUpRequest oAuthSignUpRequest, HttpServletResponse response) {
        TokenResponse tokenResponse = oAuth2SignUpService.signUp(oAuthSignUpRequest);

        response.setHeader(HttpHeaders.AUTHORIZATION, tokenResponse.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, tokenResponse.refreshTokenCookie());

        return "OAuth 로그인 유저 회원가입에 성공하였습니다.";
    }
}
