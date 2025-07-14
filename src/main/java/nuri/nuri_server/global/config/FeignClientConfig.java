package nuri.nuri_server.global.config;

import feign.codec.ErrorDecoder;
import nuri.nuri_server.global.feign.FeignClientErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "nuri.nuri_server")
public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }
}
