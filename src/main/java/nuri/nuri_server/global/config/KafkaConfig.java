package nuri.nuri_server.global.config;

import nuri.nuri_server.domain.chat.presentation.dto.res.ChatRecordResponseDto;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:29092,kafka2:29092,kafka3:29092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public ConsumerFactory<String, ChatRecordResponseDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:29092,kafka2:29092,kafka3:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(ChatRecordResponseDto.class, false)
        );
    }

    @Bean
    public NewTopic chatTopic() {
        return TopicBuilder
                .name("chat")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic userJoinTopic() {
        return TopicBuilder
                .name("user-join")
                .partitions(4)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic userExitTopic() {
        return TopicBuilder
                .name("user-exit")
                .partitions(4)
                .replicas(3)
                .build();
    }
}