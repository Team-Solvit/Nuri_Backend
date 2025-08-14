package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomFileEntity;

import java.util.UUID;

@Builder
public record BoardingRoomFileDto(
        UUID fileId,
        UUID roomId,
        String url
) {
    public static BoardingRoomFileDto from (BoardingRoomFileEntity file) {
        return BoardingRoomFileDto.builder()
                .fileId(file.getId())
                .roomId(file.getBoardingRoom().getId())
                .url(file.getMediaUrl())
                .build();
    }
}
