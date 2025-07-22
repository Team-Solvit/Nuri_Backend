package nuri.nuri_server.domain.auth.oauth2.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class OAuthTestController {

    @GetMapping("/callback") // 또는 "/kakao/callback" 같이 provider별로도 가능
    public ResponseEntity<String> receiveCode(@RequestParam String code) {
        System.out.println("🔐 받은 인가 코드: " + code);
        return ResponseEntity.ok("코드 잘 받았습니다: " + code);
    }
}
