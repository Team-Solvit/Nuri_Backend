package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import nuri.nuri_server.domain.chat.presentation.dto.common.RoomDto;

import java.time.OffsetDateTime;

@Builder
public record RoomReadResponseDto(
        @NotNull
        RoomDto roomDto,
        String latestMessage,
        OffsetDateTime latestCreatedAt
) {}