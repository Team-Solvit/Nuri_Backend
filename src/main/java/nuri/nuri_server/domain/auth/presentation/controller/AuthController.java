package nuri.nuri_server.domain.auth.presentation.controller;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.auth.application.service.AuthService;
import nuri.nuri_server.domain.auth.presentation.dto.req.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }
}
