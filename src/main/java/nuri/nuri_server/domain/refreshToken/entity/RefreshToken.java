package nuri.nuri_server.domain.refreshToken.entity;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
@Getter
@Builder
public class RefreshToken {
    @Id
    private String refreshToken;

    @Column(unique = true, nullable = false)
    private String id;

    @TimeToLive
    private Long timeToLive;
}
