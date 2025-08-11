package nuri.nuri_server.domain.chat.presentation.dto.common;

import lombok.Builder;

@Builder
public record RoomDto(
        String name,
        String profile
) {}