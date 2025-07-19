package nuri.nuri_server.domain.auth.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.application.service.AuthService;
import nuri.nuri_server.domain.auth.presentation.dto.req.LoginRequest;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import nuri.nuri_server.domain.auth.presentation.dto.res.TokenResponse;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.accessToken())
                .header(HttpHeaders.SET_COOKIE, tokenResponse.refreshTokenCookie())
                .body("로그인에 성공하였습니다.");
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request) {
        String newAccessToken = authService.reissue(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken)
                .body("토큰 재발급에 성공하였습니다.");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal NuriUserDetails nuriUserDetails) {
        String refreshTokenCookie = authService.logout(nuriUserDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body("로그아웃에 성공하였습니다.");
    }
}
