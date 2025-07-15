package nuri.nuri_server.global.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import nuri.nuri_server.domain.user.domain.role.Role;
import nuri.nuri_server.global.properties.JwtProperties;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final Long accessExpiration;
    private final Long refreshExpiration;

    @Autowired
    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.accessExpiration = jwtProperties.getAccessExpiration();
        this.refreshExpiration = jwtProperties.getRefreshExpiration();
    }

    public String createAccessToken(String userId, Role role) {
        return createJwt(userId, role, accessExpiration);
    }

    public String createRefreshToken(String userId, Role role) {
        return createJwt(userId, role, refreshExpiration);
    }

    private String createJwt(String userId, Role role, Long expiration) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .claim("madeBy", "nuri")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
    }

    public String getAccessToken(@NonNull HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidJsonWebTokenException();
        }
        return jwtVerifyAccessToken(authorizationHeader.substring(7));
    }

    private String jwtVerifyAccessToken(String token) {
        try {
            String madeBy = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("madeBy", String.class);
            if(!madeBy.equals("nuri")) throw new InvalidJsonWebTokenException();
            return token;
        } catch (JwtException e) {
            throw new InvalidJsonWebTokenException();
        }
    }
}
