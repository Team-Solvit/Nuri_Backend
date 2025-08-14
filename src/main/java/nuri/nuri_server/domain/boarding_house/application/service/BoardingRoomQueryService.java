package nuri.nuri_server.domain.boarding_house.application.service;

import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.domain.entity.BoardingRoomEntity;
import nuri.nuri_server.domain.boarding_house.domain.repository.*;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomFileDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomOptionDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.ContractPeriodDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardingRoomQueryService {
    private final BoardingRoomFileRepository boardingRoomFileRepository;
    private final ContractPeriodRepository contractPeriodRepository;
    private final BoardingRoomOptionRepository boardingRoomOptionRepository;

    public BoardingRoomDto getBoardingRoomInfo(BoardingRoomEntity room) {
        List<BoardingRoomFileDto> files = boardingRoomFileRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(BoardingRoomFileDto::from)
                .toList();

        List<BoardingRoomOptionDto> options = boardingRoomOptionRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(BoardingRoomOptionDto::from)
                .toList();

        List<ContractPeriodDto> periods = contractPeriodRepository.findAllByBoardingRoomId(room.getId()).stream()
                .map(ContractPeriodDto::from)
                .toList();

        return BoardingRoomDto.from(room, options, files, periods);
    }
}
