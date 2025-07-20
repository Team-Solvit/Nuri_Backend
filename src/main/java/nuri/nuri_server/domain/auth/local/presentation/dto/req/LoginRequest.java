package nuri.nuri_server.domain.auth.local.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "유저 id는 필수 항목입니다.")
        String id,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password
) {}
