package nuri.nuri_server.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "oauth2.provider")
public class OAuth2Properties {
    private final Map<String, OAuth2ProviderProperties> providers = new HashMap<>();
}
