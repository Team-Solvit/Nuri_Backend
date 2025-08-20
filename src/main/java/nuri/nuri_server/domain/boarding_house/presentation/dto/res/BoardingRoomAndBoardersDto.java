package nuri.nuri_server.domain.boarding_house.presentation.dto.res;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.user.presentation.dto.BoarderDto;

import java.util.List;

@Builder
public record BoardingRoomAndBoardersDto(
        BoardingRoomDto room,
        List<BoarderDto> boarders
) {}