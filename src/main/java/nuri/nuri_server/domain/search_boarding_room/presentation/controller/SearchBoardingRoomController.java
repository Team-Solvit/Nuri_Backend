package nuri.nuri_server.domain.search_boarding_room.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.search_boarding_room.application.service.SearchBoardingRoomService;
import nuri.nuri_server.domain.search_boarding_room.presentation.dto.req.BoardingRoomSearchFilterDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchBoardingRoomController {

    private final SearchBoardingRoomService searchBoardingRoomService;

    @QueryMapping
    public List<BoardingRoomDto> searchBoardingRoom(
            @Argument("boardingRoomSearchFilter") @Valid BoardingRoomSearchFilterDto boardingRoomSearchFilterDto
    ) {
        return searchBoardingRoomService.searchBoardingRoom(boardingRoomSearchFilterDto);
    }
}
