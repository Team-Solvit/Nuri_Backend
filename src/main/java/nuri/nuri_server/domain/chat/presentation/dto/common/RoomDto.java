package nuri.nuri_server.domain.chat.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;

@Builder
public record RoomDto(
        String name,
        String profile
) {
    public static RoomDto from(RoomEntity roomEntity) {
        return RoomDto.builder()
                .name(roomEntity.getName())
                .profile(roomEntity.getProfile())
                .build();
    }

}