package nuri.nuri_server.domain.chat.presentation.dto.res;

import lombok.Builder;
import nuri.nuri_server.domain.chat.presentation.dto.common.RoomDto;

import java.util.List;
import java.util.UUID;

@Builder
public record RoomCreateResponseDto(
        UUID id,
        RoomDto room,
        List<String> users
) {
}
