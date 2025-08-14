package nuri.nuri_server.domain.auth.local.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequestDto(
    @NotBlank(message = "유저명(name)은 필수 항목입니다.")
    String name,

    @NotBlank(message = "유저 아이디(id)는 필수 항목입니다.")
    String id,

    @NotBlank(message = "유저 이메일(email)은 필수 항목입니다.")
    String email,

    @NotBlank(message = "유저 비밀번호(password)는 필수 항목입니다.")
    String password,

    @NotNull(message = "유저 국적(country)은 필수 항목입니다.")
    String country,

    @NotNull(message = "유저 언어(language)는 필수 항목입니다.")
    String language,

    @Valid
    UserAgreementDto userAgreement
) {}