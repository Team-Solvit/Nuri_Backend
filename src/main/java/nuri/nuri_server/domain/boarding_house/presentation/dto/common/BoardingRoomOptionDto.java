package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomOptionEntity;

import java.util.UUID;

@Builder
public record BoardingRoomOptionDto(
        UUID optionId,
        UUID roomId,
        String name
) {
    public static BoardingRoomOptionDto from(BoardingRoomOptionEntity option) {
        return BoardingRoomOptionDto.builder()
                .optionId(option.getId())
                .roomId(option.getBoardingRoom().getId())
                .name(option.getOptionName())
                .build();
    }
}
