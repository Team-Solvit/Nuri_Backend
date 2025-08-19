package nuri.nuri_server.domain.chat.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record RoomInviteRequestDto(
        @NotNull(message = "사용자 아이디에 대한 정보가 존재해야 합니다.")
        List<@NotNull(message = "리스트 안에 null 값이 있어서는 안 됩니다.") String> users,

        @NotBlank(message = "방 아이디가 존재해야 합니다.")
        String roomId
) {}
