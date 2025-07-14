package nuri.nuri_server.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private final int port;
    private final String host;
    private final String password;
}
