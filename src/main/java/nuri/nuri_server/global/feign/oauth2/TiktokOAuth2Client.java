package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.res.information.TiktokInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.TiktokTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "TiktokOAuth", url = "https://open.tiktokapis.com")
public interface TiktokOAuth2Client {
    @PostMapping(value = "/v2/oauth/token", consumes = "application/x-www-form-urlencoded")
    TiktokTokenResponse getAccessToken(@RequestBody MultiValueMap<String, String> body);

    @GetMapping(value = "/v2/user/info/")
    TiktokInformationResponse getUserInformation(@RequestHeader("Authorization") String authorization);
}
