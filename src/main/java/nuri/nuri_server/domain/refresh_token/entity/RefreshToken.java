package nuri.nuri_server.domain.refresh_token.entity;

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
    private String userId;

    @Column(unique = true, nullable = false)
    private String refreshToken;

    @TimeToLive
    private Long timeToLive;
}
