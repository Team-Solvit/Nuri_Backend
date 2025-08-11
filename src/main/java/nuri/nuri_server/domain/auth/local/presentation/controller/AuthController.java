package nuri.nuri_server.domain.auth.local.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.local.application.service.AuthService;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.LoginRequest;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @MutationMapping
    public String localSignUp(@Argument("localSignUpInput") @Valid SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return "회원가입에 성공하였습니다.";
    }

    @MutationMapping
    public String localLogin(@Argument("localLoginInput") @Valid LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, tokenResponse.refreshTokenCookie());

        return "로그인에 성공하였습니다.";
    }

    @MutationMapping
    public String reissue() {
        String newAccessToken = authService.reissue(request);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);

        return "토큰 재발급에 성공하였습니다.";
    }

    @MutationMapping
    public String logout(@AuthenticationPrincipal NuriUserDetails nuriUserDetails) {
        String refreshTokenCookie = authService.logout(nuriUserDetails);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer ");
        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie);

        return "로그아웃에 성공하였습니다.";
    }
}
