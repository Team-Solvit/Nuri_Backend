package nuri.nuri_server.domain.auth.oauth2.presentation.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponseDto;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthLoginRequestDto;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.req.OAuthSignUpRequestDto;
import nuri.nuri_server.domain.auth.oauth2.presentation.dto.res.OAuthLoginResponseDto;
import nuri.nuri_server.domain.auth.oauth2.application.service.OAuth2SignUpService;
import nuri.nuri_server.domain.auth.oauth2.application.service.dto.OAuthLoginValue;
import nuri.nuri_server.domain.auth.oauth2.application.service.OAuth2LinkService;
import nuri.nuri_server.domain.auth.oauth2.application.service.OAuth2LoginService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2LinkService oauth2LinkService;
    private final OAuth2LoginService oauth2LoginService;
    private final OAuth2SignUpService oauth2SignUpService;
    private final HttpServletResponse response;

    @QueryMapping
    public String getOAuth2Link(@Argument String provider) {
        return oauth2LinkService.execute(provider);
    }

    @MutationMapping
    public OAuthLoginResponseDto oauthLogin(@Argument("oauthLoginInput") @Valid OAuthLoginRequestDto loginRequest) {
        String code = loginRequest.code();
        String provider = loginRequest.provider();

        String oauth2AccessToken = oauth2LoginService.getAccessToken(code, provider);
        OAuthLoginValue oauthLoginValue = oauth2LoginService.createTokenByOAuth2Token(oauth2AccessToken, provider);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthLoginValue.getAccessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, oauthLoginValue.getRefreshToken());

        return OAuthLoginResponseDto.from(oauthLoginValue);
    }

    @MutationMapping
    public String oauthSignUp(@Argument("oauthSignUpInput") @Valid OAuthSignUpRequestDto oauthSignUpRequestDto) {
        TokenResponseDto tokenResponseDto = oauth2SignUpService.signUp(oauthSignUpRequestDto);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponseDto.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, tokenResponseDto.refreshTokenCookie());

        return "OAuth 로그인 유저 회원가입에 성공하였습니다.";
    }
}
