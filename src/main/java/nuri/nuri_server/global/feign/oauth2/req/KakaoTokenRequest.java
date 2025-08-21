package nuri.nuri_server.global.feign.oauth2.req;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoTokenRequest {
    private String code;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;

    @FormProperty("redirect_uri")
    private String redirectUri;

    @FormProperty("grant_type")
    private String grantType;
}
