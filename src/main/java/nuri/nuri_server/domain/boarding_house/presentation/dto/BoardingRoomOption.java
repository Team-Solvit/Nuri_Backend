package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomOptionEntity;

import java.util.UUID;

@Builder
public record BoardingRoomOption(
        UUID optionId,
        UUID roomId,
        String name
) {
    public static BoardingRoomOption from(BoardingRoomOptionEntity option) {
        return BoardingRoomOption.builder()
                .optionId(option.getId())
                .roomId(option.getBoardingRoom().getId())
                .name(option.getOptionName())
                .build();
    }
}
