package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingHouseNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.contract.application.service.ContractQueryService;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import nuri.nuri_server.domain.user.domain.exception.HostNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.HostRepository;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetBoardingHouseService {
    private final BoardingHouseRepository boardingHouseRepository;
    private final BoardingRoomQueryService boardingRoomQueryService;
    private final BoardingRoomRepository boardingRoomRepository;
    private final ContractQueryService contractQueryService;
    private final HostRepository hostRepository;

    @Transactional(readOnly = true)
    public BoardingHouseDto getMyBoardingHouse(NuriUserDetails nuriUserDetails) {
        UUID userId = nuriUserDetails.getId();
        log.info("하숙집 정보 요청: userId={}", userId);

        validateHostId(userId);
        BoardingHouseEntity house = boardingHouseRepository.findByHostId(userId)
                .orElseThrow(BoardingHouseNotFoundException::new);

        log.info("하숙집 정보 반환: houseId={}", house.getId());

        return BoardingHouseDto.from(house);
    }

    private void validateHostId(UUID hostId) {
        if(!hostRepository.existsById(hostId)) {
            throw new HostNotFoundException(hostId.toString());
        }
    }

    @Transactional(readOnly = true)
    public List<RoomContractDto> getBoardingRoomAndBoardersInfo(UUID hostId) {
        log.info("하숙방과 하숙생 정보 요청: hostId={}", hostId);

        validateHostId(hostId);

        BoardingHouseEntity house = boardingHouseRepository.findByHostId(hostId)
                .orElseThrow(BoardingHouseNotFoundException::new);

        List<BoardingRoomEntity> boardingRooms = house.getBoardingRooms();

        List<RoomContractDto> results = boardingRooms.stream()
                .map(contractQueryService::getRoomContract)
                .toList();

        log.info("하숙방과 하숙생 정보 반환: RoomContractDtoCount={}", results.size());

        return results;
    }

    @Transactional(readOnly = true)
    public BoardingRoomDto getBoardingRoom(UUID roomId) {
        log.info("하숙방 정보 요청: roomId={}", roomId);
        BoardingRoomEntity room = boardingRoomRepository.findById(roomId)
                .orElseThrow(BoardingRoomNotFoundException::new);

        BoardingRoomDto roomInfo = boardingRoomQueryService.getBoardingRoomInfo(room);
        log.info("하숙방 정보 반환: roomInfo={}", roomInfo);
        return roomInfo;
    }
}
