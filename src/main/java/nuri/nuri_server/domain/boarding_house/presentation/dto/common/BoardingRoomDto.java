package nuri.nuri_server.domain.boarding_house.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.boarding_status.BoardingStatus;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record BoardingRoomDto(
        UUID roomId,
        BoardingHouseDto boardingHouse,
        String name,
        String description,
        Integer monthlyRent,
        Integer headCount,
        BoardingStatus status,
        List<BoardingRoomOptionDto> boardingRoomOption,
        List<BoardingRoomFileDto> boardingRoomFile,
        List<ContractPeriodDto> contractPeriod,
        LocalDate day
) {
    public static BoardingRoomDto from(BoardingRoomEntity room, List<BoardingRoomOptionDto> option, List<BoardingRoomFileDto> file, List<ContractPeriodDto> contractPeriod) {
        BoardingHouseDto house = BoardingHouseDto.from(room.getBoardingHouse());
        return BoardingRoomDto.builder()
                .roomId(room.getId())
                .boardingHouse(house)
                .name(room.getName())
                .description(room.getDescription())
                .monthlyRent(room.getMonthlyRent())
                .headCount(room.getHeadCount())
                .status(room.getStatus())
                .boardingRoomOption(option)
                .boardingRoomFile(file)
                .contractPeriod(contractPeriod)
                .day(room.getUpdatedAt().toLocalDate())
                .build();
    }
}
