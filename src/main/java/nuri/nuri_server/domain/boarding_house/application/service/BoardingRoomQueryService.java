package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomFile;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomOption;
import nuri.nuri_server.domain.boarding_house.presentation.dto.ContractPeriod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardingRoomQueryService {
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final ContractPeriodRepository contractPeriodRepository;
    private final BoardingRoomOptionRepository boardingRoomOptionRepository;

    public BoardingRoomInfo getBoardingRoomInfo(BoardingRoomEntity room) {
        List<BoardingRoomFile> files = boardingRoomFileRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(BoardingRoomFile::from)
                .toList();

        List<BoardingRoomOption> options = boardingRoomOptionRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(BoardingRoomOption::from)
                .toList();

        List<ContractPeriod> periods = contractPeriodRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(ContractPeriod::from)
                .toList();

        return BoardingRoomInfo.from(room, options, files, periods);
    }
}
