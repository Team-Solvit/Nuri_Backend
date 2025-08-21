package nuri.nuri_server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class NuriServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuriServerApplication.class, args);
    }

}
