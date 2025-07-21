package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2.provider")
public class OAuth2Properties {
    private OAuth2ProviderProperties google;
    private OAuth2ProviderProperties facebook;
    private OAuth2ProviderProperties tiktok;
    private OAuth2ProviderProperties kakao;

    @Getter
    @Setter
    public static class OAuth2ProviderProperties {
        private String baseUrl;
        private String clientId;
        private String redirectUrl;
        private String clientSecret;
        private String grantType;
    }
}
