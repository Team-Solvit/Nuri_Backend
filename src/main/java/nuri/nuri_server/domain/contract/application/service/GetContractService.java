package nuri.nuri_server.domain.contract.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetContractService {

    private final ContractQueryService contractQueryService;
    private final BoardingRoomRepository boardingRoomRepository;

    @Transactional(readOnly = true)
    public RoomContractDto getRoomContract(UUID roomId) {
        log.info("하숙방 계약 조회 요청: roomId={}", roomId);
        BoardingRoomEntity room = boardingRoomRepository.findById(roomId)
                .orElseThrow(BoardingRoomNotFoundException::new);

        RoomContractDto roomContract = contractQueryService.getRoomContract(room);
        log.info("하숙방 계약 조회 반환: contractInfoCount={}", roomContract.contractInfo().size());
        return roomContract;
    }
}
