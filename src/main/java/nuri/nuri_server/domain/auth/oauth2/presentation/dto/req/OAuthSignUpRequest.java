package nuri.nuri_server.domain.auth.oauth2.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nuri.nuri_server.domain.auth.local.presentation.dto.req.UserAgreement;

public record OAuthSignUpRequest(
        @NotBlank(message = "OAuth 서비스 id(oAuthId)는 필수 항목입니다.")
        String oAuthId,

        @NotBlank(message = "유저 아이디(id)는 필수 항목입니다.")
        String id,

        @NotBlank(message = "유저 이메일(email)은 필수 항목입니다.")
        String email,

        @NotNull(message = "유저 국적(country)은 필수 항목입니다.")
        String country,

        @NotNull(message = "유저 언어(language)는 필수 항목입니다.")
        String language,

        @Valid
        UserAgreement userAgreement
) {}
