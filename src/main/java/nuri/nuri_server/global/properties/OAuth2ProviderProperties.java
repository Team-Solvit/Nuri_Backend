package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2ProviderProperties {
    private String baseUrl;
    private String clientId;
    private String redirectUrl;
    private String clientSecret;
    private String grantType;
}