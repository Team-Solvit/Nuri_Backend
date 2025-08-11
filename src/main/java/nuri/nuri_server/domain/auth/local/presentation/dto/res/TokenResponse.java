package nuri.nuri_server.domain.auth.local.presentation.dto.res;

public record TokenResponse(
        String accessToken,
        String refreshTokenCookie
) {}
