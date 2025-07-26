package nuri.nuri_server.global.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import nuri.nuri_server.domain.auth.local.presentation.dto.res.TokenResponse;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
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
    private final CookieManager cookieManager;

    public TokenResponse createTokenResponse(UserEntity userEntity) {
        String accessToken = createAccessToken(userEntity.getUserId(), userEntity.getRole());
        String refreshToken = createRefreshToken(userEntity.getUserId());

        String refreshTokenCookie = cookieManager.createRefreshTokenCookie(userEntity.getUserId(), refreshToken);

        return new TokenResponse(accessToken, refreshTokenCookie);
    }

    @Autowired
    public JwtProvider(JwtProperties jwtProperties, CookieManager cookieManager) {
        this.secretKey = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.accessExpiration = jwtProperties.getAccessExpiration();
        this.refreshExpiration = jwtProperties.getRefreshExpiration();
        this.cookieManager = cookieManager;
    }

    public String createAccessToken(String userId, Role role) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .claim("madeBy", "nuri")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .claim("madeBy", "nuri")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getAccessToken(String authorizationHeader) {
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
