package nuri.nuri_server.global.feign.oauth2;

import nuri.nuri_server.global.feign.oauth2.res.information.FacebookInformationResponse;
import nuri.nuri_server.global.feign.oauth2.res.token.FacebookTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facebookOAuth", url = "https://graph.facebook.com")
public interface FacebookOAuth2Client {
    @GetMapping(value = "/v23.0/oauth/access_token?client_id={client_id}" +
            "&redirect_uri={redirect_uri}" +
            "&client_secret={client_secret}" +
            "&code={code}", consumes = "application/x-www-form-urlencoded")
    FacebookTokenResponse getAccessToken(
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code
    );

    @GetMapping(value = "/v23.0/me?fields=id,name,picture&access_token={TOKEN}")
    FacebookInformationResponse getUserInformation(@PathVariable("TOKEN") String accessToken);
}
