package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticsearchProperties {
    private final String uris;
}
