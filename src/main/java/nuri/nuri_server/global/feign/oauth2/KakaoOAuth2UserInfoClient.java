package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.res.information.KakaoInformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoOAuthUserInfo", url = "https://kapi.kakao.com")
public interface KakaoOAuth2UserInfoClient {
    @GetMapping(value = "/v2/user/me")
    KakaoInformationResponse getUserInformation(@RequestHeader("Authorization") String authorization);
}
