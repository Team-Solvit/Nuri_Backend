package nuri.nuri_server.domain.session.domain.entity;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "session")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionEntity {
    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String sessionId;

    @TimeToLive
    private Long timeToLive;
}
