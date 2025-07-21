package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.res.information.KakaoInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kakaoOAuth", url = "https://kapi.kakao.com")
public interface KakaoOAuth2Client {
    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded")
    KakaoTokenResponse getAccessToken(@RequestBody MultiValueMap<String, String> body);

    @GetMapping(value = "/v2/user/me")
    KakaoInformationResponse getUserInformation(@RequestHeader("Authorization") String authorization);
}
