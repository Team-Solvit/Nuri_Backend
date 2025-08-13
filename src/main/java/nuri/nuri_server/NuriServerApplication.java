package nuri.nuri_server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NuriServerApplication {

    @Value("${spring.datasource.url}")
    private static String url;

    @Value("${spring.datasource.password}")
    private static String password;

    public static void main(String[] args) {
        log.info(url);
        log.info(password);
        SpringApplication.run(NuriServerApplication.class, args);
    }

}
