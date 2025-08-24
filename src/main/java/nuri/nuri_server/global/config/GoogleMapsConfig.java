package nuri.nuri_server.global.config;

import com.google.maps.GeoApiContext;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.properties.GoogleMapsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GoogleMapsConfig {

    private final GoogleMapsProperties googleMapsProperties;

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(googleMapsProperties.getApiKey())
                .build();
    }
}
