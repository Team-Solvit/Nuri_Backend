package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.res.information.GoogleInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "googleOAuth", url = "https://oauth2.googleapis.com")
public interface GoogleOAuth2Client {
    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    GoogleTokenResponse getAccessToken(@RequestBody MultiValueMap<String, String> body);

    @GetMapping(value = "/oauth2/v1/userinfo?alt=json&access_token={TOKEN}")
    GoogleInformationResponse getUserInformation(@PathVariable("TOKEN") String accessToken);
}
