package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomFileEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomOptionEntity;
import nuri.nuri_server.domain.boarding_house.domain.entity.ContractPeriodEntity;
import nuri.nuri_server.domain.boarding_house.domain.exception.BoardingRoomNotFoundException;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomFileRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomOptionRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.BoardingRoomRepository;
import nuri.nuri_server.domain.boarding_house.domain.repository.ContractPeriodRepository;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomUpsertDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomUpdateRequest;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateBoardingHouseService {
    private final BoardingRoomRepository boardingRoomRepository;
    private final BoardingRoomOptionRepository boardingRoomOptionRepository;
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final ContractPeriodRepository contractPeriodRepository;

    @Transactional
    public void updateBoardingRoomInfo(NuriUserDetails nuriUserDetails, BoardingRoomUpdateRequest boardingRoomUpdateRequest) {
        log.info("하숙방 정보 수정 요청: userId={}, roomId={}",
                nuriUserDetails.getId(), boardingRoomUpdateRequest.roomId());

        BoardingRoomEntity room = boardingRoomRepository.findById(boardingRoomUpdateRequest.roomId())
                .orElseThrow(BoardingRoomNotFoundException::new);

        room.validateHost(nuriUserDetails.getUser());

        BoardingRoomUpsertDto roomInfo = boardingRoomUpdateRequest.boardingRoomInfo();
        room.updateBoardingRoom(
                roomInfo.name(),
                roomInfo.description(),
                roomInfo.monthlyRent(),
                roomInfo.headCount()
        );

        updateContractPeriod(boardingRoomUpdateRequest.contractPeriod(), room);
        updateBoardingRoomOption(boardingRoomUpdateRequest.options(), room);
        updateBoardingRoomFile(boardingRoomUpdateRequest.files(), room);

        log.info("하숙방 정보 수정 요청: userId={}, roomId={}",
                nuriUserDetails.getId(), room.getId());
    }

    private void updateBoardingRoomFile(List<String> files, BoardingRoomEntity room) {
        boardingRoomFileRepository.deleteAllByBoardingRoomId(room.getId());

        List<BoardingRoomFileEntity> fileEntities = files.stream()
                .map(url -> BoardingRoomFileEntity.builder()
                        .mediaUrl(url)
                        .boardingRoom(room)
                        .build())
                .toList();

        boardingRoomFileRepository.saveAll(fileEntities);
    }

    private void updateBoardingRoomOption(List<String> options, BoardingRoomEntity room) {
        boardingRoomOptionRepository.deleteAllByBoardingRoomId(room.getId());

        List<BoardingRoomOptionEntity> optionEntities = options.stream()
                .map(option -> BoardingRoomOptionEntity.builder()
                        .optionName(option)
                        .boardingRoom(room)
                        .build())
                .toList();

        boardingRoomOptionRepository.saveAll(optionEntities);
    }

    private void updateContractPeriod(List<Integer> contractPeriod, BoardingRoomEntity room) {
        contractPeriodRepository.deleteAllByBoardingRoomId(room.getId());

        List<ContractPeriodEntity> contractPeriodEntities = contractPeriod.stream()
                .map(period -> ContractPeriodEntity.builder()
                        .contractPeriod(period)
                        .boardingRoom(room)
                        .build())
                .toList();

        contractPeriodRepository.saveAll(contractPeriodEntities);
    }
}
