package nuri.nuri_server.global.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2.provider")
public class OAuth2Properties {
    private OAuth2ProviderProperties google;
    private OAuth2ProviderProperties facebook;
    private OAuth2ProviderProperties tiktok;
    private OAuth2ProviderProperties kakao;
}
