package nuri.nuri_server.domain.contract.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomQueryService;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.ContractEntity;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.contract.presentation.dto.common.ContractInfoDto;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContractQueryService {

    private final BoardingRoomQueryService boardingRoomQueryService;

    public RoomContractDto getRoomContract(BoardingRoomEntity boardingRoom) {
        BoardingRoomDto room = boardingRoomQueryService.getBoardingRoomInfo(boardingRoom);
        List<ContractInfoDto> contractInfos = boardingRoom.getContracts().stream()
                .filter(ContractEntity::isActive)
                .map(ContractInfoDto::from)
                .toList();

        return RoomContractDto.builder()
                .room(room)
                .contractInfo(contractInfos)
                .build();
    }
}
