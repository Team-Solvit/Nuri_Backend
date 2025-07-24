package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.config.FeignClientConfig;
import nuri.nuri_server.global.feign.oauth2.req.GoogleTokenRequest;
import nuri.nuri_server.global.feign.oauth2.res.token.GoogleTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "googleOAuthToken", url = "https://oauth2.googleapis.com", configuration = FeignClientConfig.class)
public interface GoogleOAuth2TokenClient {
    @PostMapping(
            value = "/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    GoogleTokenResponse getAccessToken(@RequestBody GoogleTokenRequest googleTokenRequest);
}
