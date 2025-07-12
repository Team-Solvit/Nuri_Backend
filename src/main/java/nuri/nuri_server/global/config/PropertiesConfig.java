package nuri.nuri_server.global.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "nuri.nuri_server.global.properties")
public class PropertiesConfig {
}