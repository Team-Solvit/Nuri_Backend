package nuri.nuri_server.global.feign.oauth2.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoTokenRequest {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
}
