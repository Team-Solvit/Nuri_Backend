package nuri.nuri_server.domain.chat.presentation.dto.res;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import nuri.nuri_server.domain.chat.domain.entity.ChatRecord;
import nuri.nuri_server.domain.chat.domain.entity.RoomEntity;
import nuri.nuri_server.domain.chat.presentation.dto.common.RoomDto;

import java.time.OffsetDateTime;

@Builder
public record RoomReadResponseDto(
        @NotNull
        RoomDto roomDto,
        String latestMessage,
        OffsetDateTime latestCreatedAt,
        @NotNull
        Long newMessageCount
) {
        public static RoomReadResponseDto from(ChatRecord latestMessage, long newMessageCount, RoomEntity roomEntity) {
                return RoomReadResponseDto.builder()
                        .roomDto(RoomDto.from(roomEntity))
                        .latestMessage(latestMessage != null ? latestMessage.getContents() : "")
                        .latestCreatedAt(latestMessage != null ? latestMessage.getCreatedAt() : null)
                        .newMessageCount(newMessageCount)
                        .build();
        }
}