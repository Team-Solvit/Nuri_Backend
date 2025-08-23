package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "google.maps")
public class GoogleMapsProperties {
    private final String apiKey;
}
