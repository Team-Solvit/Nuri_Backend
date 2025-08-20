package nuri.nuri_server.domain.auth.local.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.local.application.service.AuthService;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.LoginRequestDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.SignupRequestDto;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponseDto;
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
    public String localSignUp(@Argument("localSignUpInput") @Valid SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return "회원가입에 성공하였습니다.";
    }

    @MutationMapping
    public String localLogin(@Argument("localLoginInput") @Valid LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = authService.login(loginRequestDto);

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponseDto.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, tokenResponseDto.refreshTokenCookie());

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
