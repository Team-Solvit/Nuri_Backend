package nuri.nuri_server.global.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import nuri.nuri_server.domain.user.domain.entity.UserEntity;
import nuri.nuri_server.global.properties.JwtProperties;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final Long accessExpiration;
    private final Long refreshExpiration;

    @Autowired
    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.accessExpiration = jwtProperties.getAccessExpiration();
        this.refreshExpiration = jwtProperties.getRefreshExpiration();
    }

    public String createAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .setSubject(userEntity.getId())
                .claim("role", userEntity.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getAccessToken(@NonNull HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(!authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidJsonWebTokenException();
        }
        return jwtVerifyAccessToken(authorizationHeader.substring(7));
    }

    private String jwtVerifyAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken);
            return accessToken;
        } catch (JwtException e) {
            throw new InvalidJsonWebTokenException();
        }
    }
}
