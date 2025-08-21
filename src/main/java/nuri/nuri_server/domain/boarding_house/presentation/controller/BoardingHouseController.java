package nuri.nuri_server.domain.boarding_house.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.CreateBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.application.service.DeleteBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.application.service.GetBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.application.service.UpdateBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomCreateRequestDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomUpdateRequest;
import nuri.nuri_server.domain.boarding_house.presentation.dto.res.BoardingRoomAndBoardersDto;
import nuri.nuri_server.global.security.annotation.Host;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardingHouseController {

    private final CreateBoardingHouseService createBoardingHouseService;
    private final UpdateBoardingHouseService updateBoardingHouseService;
    private final GetBoardingHouseService getBoardingHouseService;
    private final DeleteBoardingHouseService deleteBoardingHouseService;

    @Host
    @MutationMapping
    public String createBoardingRoom(
            @Argument("boardingRoomCreateInput") @Valid BoardingRoomCreateRequestDto boardingRoomCreateRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        createBoardingHouseService.createBoardingRoom(nuriUserDetails, boardingRoomCreateRequestDto);
        return "하숙방을 추가하였습니다";
    }

    @Host
    @MutationMapping
    public String updateBoardingRoom(
            @Argument("boardingRoomUpdateInput") @Valid BoardingRoomUpdateRequest boardingRoomUpdateRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        updateBoardingHouseService.updateBoardingRoomInfo(nuriUserDetails, boardingRoomUpdateRequest);
        return "하숙방 정보를 수정하였습니다.";
    }

    @Host
    @MutationMapping
    public String deleteBoardingRoom(
            @Argument("roomId") @NotNull(message = "하숙방 삭제시 방 아이디(roomId)는 필수 항목입니다.") UUID roomId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        deleteBoardingHouseService.deleteBoardingRoom(nuriUserDetails, roomId);
        return "하숙방을 삭제하였습니다";
    }

    @Host
    @QueryMapping
    public BoardingHouseDto getMyBoardingHouse(
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getBoardingHouseService.getMyBoardingHouse(nuriUserDetails);
    }

    @Host
    @QueryMapping
    public List<BoardingRoomAndBoardersDto> getBoardingRoomAndBoardersInfoList(
            @Argument("userId") UUID userId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        if(userId == null) userId = nuriUserDetails.getId();

        return getBoardingHouseService.getBoardingRoomAndBoardersInfo(userId);
    }

    @QueryMapping
    public BoardingRoomDto getBoardingRoom(
            @Argument("roomId") @NotNull(message = "하숙방 조회시 방 아이디(roomId)는 필수 항목입니다.") UUID roomId
    ) {
        return getBoardingHouseService.getBoardingRoom(roomId);
    }
}
