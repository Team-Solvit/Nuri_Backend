package nuri.nuri_server.global.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.refresh_token.entity.RefreshToken;
import nuri.nuri_server.domain.refresh_token.repository.RefreshTokenRepository;
import nuri.nuri_server.domain.user.domain.exception.UserNotFoundException;
import nuri.nuri_server.global.properties.JwtProperties;
import nuri.nuri_server.global.security.exception.InvalidJsonWebTokenException;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CookieManager {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshTokenCookie(String userId, String refreshToken) {
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .refreshToken(refreshToken)
                        .timeToLive(jwtProperties.getRefreshExpiration())
                        .build()
        );
        return createRefreshTokenCookie(refreshToken, jwtProperties.getRefreshExpiration()).toString();
    }

    private ResponseCookie createRefreshTokenCookie(String value, Long maxAge) {
        return ResponseCookie.from("refresh", value)
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
                .findFirst()
                .orElseThrow(InvalidJsonWebTokenException::new);
    }

    public String deleteRefreshToken(String userId) {
        refreshTokenRepository.deleteById(userId);
        return createRefreshTokenCookie("", 0L).toString();
    }

    public void validateRefreshToken(String userId, String refreshToken) {
        RefreshToken refreshTokenObject = refreshTokenRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if(!refreshTokenObject.getRefreshToken().equals(refreshToken)) throw new InvalidJsonWebTokenException();
    }
}
