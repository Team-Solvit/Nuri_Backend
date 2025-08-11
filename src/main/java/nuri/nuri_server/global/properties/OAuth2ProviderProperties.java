package nuri.nuri_server.global.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2ProviderProperties {
    private String baseUrl;
    private String clientId;
    private String redirectUrl;
    private String clientSecret;
    private String grantType;
}
