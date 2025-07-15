package nuri.nuri_server.global.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.refresh_token.entity.RefreshToken;
import nuri.nuri_server.domain.refresh_token.repository.RefreshTokenRepository;
import nuri.nuri_server.global.properties.JwtProperties;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CookieManager {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshTokenCookie(String userId, String refreshToken) {
        refreshTokenRepository.deleteAllById(Collections.singleton(userId));
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .refreshToken(refreshToken)
                        .timeToLive(jwtProperties.getRefreshExpiration())
                        .build()
        );
        return createRefreshCookie(refreshToken, jwtProperties.getRefreshExpiration()).toString();
    }

    private ResponseCookie createRefreshCookie(String value, Long maxAge) {
        return ResponseCookie.from("refresh", value)
                .maxAge(maxAge)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
    }

    public String deleteRefreshToken(String userId, String refreshToken) {
        refreshTokenRepository.deleteById(userId);
        return createRefreshCookie(refreshToken, 0L).toString();
    }

    public void checkRefreshToken(String userId, HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new InvalidJsonWebTokenException();
        }

        RefreshToken refreshTokenObject = refreshTokenRepository.findById(userId).orElseThrow(InvalidJsonWebTokenException::new);

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(InvalidJsonWebTokenException::new);

        if(!refreshTokenObject.getRefreshToken().equals(refreshToken)) throw new InvalidJsonWebTokenException();
    }
}
