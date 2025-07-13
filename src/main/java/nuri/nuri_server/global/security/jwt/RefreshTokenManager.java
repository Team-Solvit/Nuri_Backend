package nuri.nuri_server.global.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.refreshToken.entity.RefreshToken;
import nuri.nuri_server.domain.refreshToken.repository.RefreshTokenRepository;
import nuri.nuri_server.global.properties.JwtProperties;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(String id, String refreshToken) {
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(id)
                        .refreshToken(refreshToken)
                        .timeToLive(jwtProperties.getRefreshExpiration())
                        .build()
        );
        return createRefreshCookie(refreshToken, jwtProperties.getRefreshExpiration()).toString();
    }

    public ResponseCookie createRefreshCookie(String refreshToken) {
        Long defaultMaxAge = jwtProperties.getRefreshExpiration();
        return createRefreshCookie(refreshToken, defaultMaxAge);
    }

    public ResponseCookie createRefreshCookie(String refreshToken, Long maxAge) {
        return ResponseCookie.from("refresh", refreshToken)
                .maxAge(maxAge)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new InvalidJsonWebTokenException();

        return Arrays.stream(cookies)
                .filter(c -> "refresh".equals(c.getName()))
                .map(Cookie::getValue)
                .filter(refreshTokenRepository::existsById)
                .findFirst()
                .orElseThrow(InvalidJsonWebTokenException::new);
    }

    public String deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
        return createRefreshCookie("", 0L).toString();
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(InvalidJsonWebTokenException::new).getId();
    }
}
