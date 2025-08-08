package nuri.nuri_server.domain.boarding_house.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.domain.boarding_status.BoardingStatus;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.user.presentation.dto.HostInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record BoardingRoomInfo(
        UUID roomId,
        UUID boardingHouseId,
        String name,
        String description,
        Integer monthlyRent,
        Integer headCount,
        BoardingStatus status,
        List<BoardingRoomOption> boardingRoomOption,
        List<BoardingRoomFile> boardingRoomFile,
        List<ContractPeriod> contractPeriod,
        LocalDate day,
        HostInfo host
) {
    public static BoardingRoomInfo from(BoardingRoomEntity room, List<BoardingRoomOption> option, List<BoardingRoomFile> file, List<ContractPeriod> contractPeriod) {
        HostInfo host = HostInfo.from(room.getBoardingHouse().getHost());
        return BoardingRoomInfo.builder()
                .roomId(room.getId())
                .boardingHouseId(room.getBoardingHouse().getId())
                .name(room.getName())
                .description(room.getDescription())
                .monthlyRent(room.getMonthlyRent())
                .headCount(room.getHeadCount())
                .status(room.getStatus())
                .boardingRoomOption(option)
                .boardingRoomFile(file)
                .contractPeriod(contractPeriod)
                .day(room.getUpdatedAt().toLocalDate())
                .host(host)
                .build();
    }
}
