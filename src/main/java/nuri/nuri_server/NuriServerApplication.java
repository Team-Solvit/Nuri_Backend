package nuri.nuri_server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class NuriServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(NuriServerApplication.class, args);
        Environment env = ctx.getEnvironment();
        String url = env.getProperty("spring.datasource.url");
        log.info(url);
        SpringApplication.run(NuriServerApplication.class, args);
    }

}
