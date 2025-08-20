package nuri.nuri_server.domain.auth.oauth2.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;

public record OAuthLoginRequestDto(
        @NotBlank(message = "code는 필수 항목입니다.")
        String code,

        @NotBlank(message = "provider는 필수 항목입니다.")
        String provider
) {}