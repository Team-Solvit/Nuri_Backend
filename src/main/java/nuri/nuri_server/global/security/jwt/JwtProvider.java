package nuri.nuri_server.global.security.jwt;

import io.jsonwebtoken.security.Keys;
import nuri.nuri_server.global.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final String accessExpiration;
    private final String refreshExpiration;

    @Autowired
    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.accessExpiration = jwtProperties.getAccessExpiration();
        this.refreshExpiration = jwtProperties.getRefreshExpiration();
    }
}
