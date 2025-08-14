package nuri.nuri_server.domain.auth.local.presentation.dto.res;

public record TokenResponseDto(
        String accessToken,
        String refreshTokenCookie
) {}
