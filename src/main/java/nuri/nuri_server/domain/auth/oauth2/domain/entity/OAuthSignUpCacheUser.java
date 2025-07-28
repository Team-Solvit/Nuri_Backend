package nuri.nuri_server.domain.auth.oauth2.domain.entity;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
@Getter
@Builder
public class OAuthSignUpCacheUser {
    @Id
    private String oauthId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String profile;

    @Column(unique = true, nullable = false)
    private String provider;

    @Column(unique = true, nullable = false)
    private String id;

    @TimeToLive
    private Long timeToLive;
}
