package nuri.nuri_server.global.config;

import nuri.nuri_server.global.converter.DateToOffsetDateTimeConverter;
import nuri.nuri_server.global.converter.OffsetDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new DateToOffsetDateTimeConverter(),
                new OffsetDateTimeToDateConverter()
        ));
    }
}
