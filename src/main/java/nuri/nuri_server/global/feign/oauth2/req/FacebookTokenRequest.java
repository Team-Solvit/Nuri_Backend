package nuri.nuri_server.global.feign.oauth2.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FacebookTokenRequest {
    private final String code;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
}
