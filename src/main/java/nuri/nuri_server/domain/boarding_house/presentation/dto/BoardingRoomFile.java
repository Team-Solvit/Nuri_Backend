package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomFileEntity;

import java.util.UUID;

@Builder
public record BoardingRoomFile(
        UUID fileId,
        UUID roomId,
        String url
) {
    public static BoardingRoomFile from (BoardingRoomFileEntity file) {
        return BoardingRoomFile.builder()
                .fileId(file.getId())
                .roomId(file.getBoardingRoom().getId())
                .url(file.getMediaUrl())
                .build();
    }
}
