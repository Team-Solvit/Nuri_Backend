package nuri.nuri_server.domain.contract.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;

import java.util.List;

@Builder
public record RoomContractDto(
        BoardingRoomDto room,
        List<ContractInfoDto> contractInfo
) {}