package nuri.nuri_server.domain.chat.presentation.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;

import java.util.UUID;

@Builder
public record RoomDto(
        @NotBlank(message = "아이디는 존재해야 합니다.")
        UUID id,
        @NotBlank(message = "이름이 존재해야 합니다.")
        String name,
        String profile
) {
    public static RoomDto from(RoomEntity roomEntity) {
        return RoomDto.builder()
                .id(roomEntity.getId())
                .name(roomEntity.getName())
                .profile(roomEntity.getProfile())
                .build();
    }

}