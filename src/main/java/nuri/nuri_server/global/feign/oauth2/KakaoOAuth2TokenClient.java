package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.req.KakaoTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.token.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kakaoOAuthToken", url = "https://kauth.kakao.com")
public interface KakaoOAuth2TokenClient {
    @PostMapping(
            value = "/oauth/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    KakaoTokenResponse getAccessToken(@RequestBody KakaoTokenRequest kakaoTokenRequest);
}
