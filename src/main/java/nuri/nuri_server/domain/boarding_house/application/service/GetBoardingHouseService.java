package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingHouseEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingHouseNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingHouseInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.response.BoardingRoomAndBoardersInfo;
import nuri.nuri_server.domain.user.domain.exception.HostNotFoundException;
import nuri.nuri_server.domain.user.domain.repository.HostRepository;
import nuri.nuri_server.domain.user.presentation.dto.BoarderInfo;
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
    private final HostRepository hostRepository;

    @Transactional(readOnly = true)
    public BoardingHouseInfo getMyBoardingHouse(NuriUserDetails nuriUserDetails) {
        UUID userId = nuriUserDetails.getId();
        log.info("하숙집 정보 요청: userId={}", userId);

        validateHostId(userId);
        BoardingHouseEntity house = boardingHouseRepository.findByHostId(userId)
                .orElseThrow(BoardingHouseNotFoundException::new);

        log.info("하숙집 정보 반환: houseId={}", house.getId());

        return BoardingHouseInfo.from(house);
    }

    private void validateHostId(UUID hostId) {
        if(!hostRepository.existsById(hostId)) {
            throw new HostNotFoundException(hostId.toString());
        }
    }

    @Transactional(readOnly = true)
    public List<BoardingRoomAndBoardersInfo> getBoardingRoomAndBoardersInfo(UUID hostId) {
        log.info("하숙방과 하숙생 정보 요청: hostId={}", hostId);

        validateHostId(hostId);

        BoardingHouseEntity house = boardingHouseRepository.findByHostId(hostId)
                .orElseThrow(BoardingHouseNotFoundException::new);

        List<BoardingRoomEntity> boardingRooms = house.getBoardingRooms();

        List<BoardingRoomAndBoardersInfo> results = boardingRooms.stream()
                .map(this::toBoardingRoomAndBoardersInfo)
                .toList();

        log.info("하숙방과 하숙생 정보 반환: roomAndBoardersInfoCount={}", results.size());

        return results;
    }

    private BoardingRoomAndBoardersInfo toBoardingRoomAndBoardersInfo(BoardingRoomEntity boardingRoom) {
        BoardingRoomInfo room = boardingRoomQueryService.getBoardingRoomInfo(boardingRoom);
        List<BoarderInfo> boarders = boardingRoom.getContracts().stream()
                .map(contract -> BoarderInfo.from(contract.getBoarder()))
                .toList();

        return BoardingRoomAndBoardersInfo.builder()
                .room(room)
                .boarders(boarders)
                .build();
    }

    @Transactional(readOnly = true)
    public BoardingRoomInfo getBoardingRoom(UUID roomId) {
        log.info("하숙방 정보 요청: roomId={}", roomId);
        BoardingRoomEntity room = boardingRoomRepository.findById(roomId)
                .orElseThrow(BoardingRoomNotFoundException::new);

        BoardingRoomInfo roomInfo = boardingRoomQueryService.getBoardingRoomInfo(room);
        log.info("하숙방 정보 반환: roomInfo={}", roomInfo);
        return roomInfo;
    }
}
