package nuri.nuri_server.domain.chat.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserExitRequestDto(
        @NotNull(message = "방 아이디 값은 존재해야 합니다.")
        UUID roomId,

        @NotNull(message = "인원 수는 존재해야 합니다.")
        Integer participantNumber,

        @NotNull(message = "유저 아이디는 존재해야 합니다.")
        String userId
) {}
