package nuri.nuri_server.domain.boarding_house.presentation.dto.response;

import lombok.Builder;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomInfo;
import nuri.nuri_server.domain.user.presentation.dto.BoarderInfo;

import java.util.List;

@Builder
public record BoardingRoomAndBoardersInfo(
        BoardingRoomInfo room,
        List<BoarderInfo> boarders
) {}