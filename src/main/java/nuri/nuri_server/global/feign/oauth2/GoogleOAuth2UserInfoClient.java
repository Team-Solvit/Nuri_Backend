package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.config.FeignClientConfig;
import nuri.nuri_server.global.feign.oauth2.res.information.GoogleInformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleOAuthUserInfo", url = "https://www.googleapis.com", configuration = FeignClientConfig.class)
public interface GoogleOAuth2UserInfoClient {
    @GetMapping(value = "/oauth2/v3/userinfo", consumes = MediaType.APPLICATION_JSON_VALUE)
    GoogleInformationResponse getUserInformation(@RequestHeader("Authorization") String accessToken);
}
