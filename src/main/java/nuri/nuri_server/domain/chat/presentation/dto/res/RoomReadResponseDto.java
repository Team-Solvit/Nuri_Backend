package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import nuri.nuri_server.domain.chat.presentation.dto.common.RoomDto;

import java.time.OffsetDateTime;

public record RoomReadResponseDto(
        @NotNull
        RoomDto roomDto,
        String latestMessage,
        OffsetDateTime latestCreatedAt
) {}