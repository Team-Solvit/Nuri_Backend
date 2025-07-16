package nuri.nuri_server.global.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.global.properties.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@RequiredArgsConstructor
public class MongoConfig {
    private final MongoProperties mongoProperties;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoProperties.getClient());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), mongoProperties.getName());
    }
}
