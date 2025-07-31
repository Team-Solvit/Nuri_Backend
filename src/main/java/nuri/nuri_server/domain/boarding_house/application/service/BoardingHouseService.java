package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.*;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingHouseNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.BoardingRoomInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.CreateBoardingRoomRequest;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardingHouseService {

    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingHouseRepository boardingHouseRepository;
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final ContractPeriodRepository contractPeriodRepository;
    private final BoardingRoomOptionRepository boardingRoomOptionRepository;

    public void createBoardingRoom(NuriUserDetails nuriUserDetails, CreateBoardingRoomRequest createBoardingRoomRequest) {
        BoardingHouseEntity house = getBoardingHouseByHostId(nuriUserDetails.getUser().getId());
        BoardingRoomEntity room = boardingRoomRepository.save(toBoardingRoomEntity(house, createBoardingRoomRequest.boardingRoomInfo()));
        boardingRoomFileRepository.saveAll(toBoardingRoomFileList(room, createBoardingRoomRequest.files()));
        contractPeriodRepository.saveAll(toContractPeriodList(room, createBoardingRoomRequest.contractPeriod()));
        boardingRoomOptionRepository.saveAll(toOptionList(room, createBoardingRoomRequest.options()));
    }

    private BoardingHouseEntity getBoardingHouseByHostId(UUID hostId) {
        return boardingHouseRepository.findByHostId(hostId)
                .orElseThrow(BoardingHouseNotFoundException::new);
    }

    private BoardingRoomEntity toBoardingRoomEntity(BoardingHouseEntity boardingHouse, BoardingRoomInfo boardingRoom) {
        return BoardingRoomEntity.builder()
                .boardingHouse(boardingHouse)
                .name(boardingRoom.name())
                .description(boardingRoom.description())
                .monthlyRent(boardingRoom.monthlyRent())
                .headCount(boardingRoom.headCount())
                .build();
    }

    private List<BoardingRoomFileEntity> toBoardingRoomFileList(BoardingRoomEntity boardingRoom, List<String> files) {
        return files.stream()
                .map(url -> BoardingRoomFileEntity.builder()
                        .boardingRoom(boardingRoom)
                        .mediaUrl(url)
                        .build())
                .toList();
    }

    private List<ContractPeriodEntity> toContractPeriodList(BoardingRoomEntity boardingRoom, List<Integer> contractPeriods) {
        return contractPeriods.stream()
                .map(period -> ContractPeriodEntity.builder()
                        .boardingRoom(boardingRoom)
                        .contractPeriod(period)
                        .build())
                .toList();
    }

    private List<BoardingRoomOptionEntity> toOptionList(BoardingRoomEntity boardingRoom, List<String> options) {
         return options.stream()
                .map(option -> BoardingRoomOptionEntity.builder()
                        .boardingRoom(boardingRoom)
                        .optionName(option)
                        .build())
                .toList();
    }
}
