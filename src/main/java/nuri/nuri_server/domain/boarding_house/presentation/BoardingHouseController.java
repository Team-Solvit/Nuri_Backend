package nuri.nuri_server.domain.boarding_house.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.CreateBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.application.service.GetBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingHouseInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.CreateBoardingRoomRequest;
import nuri.nuri_server.domain.boarding_house.presentation.dto.response.BoardingRoomAndBoardersInfo;
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
    private final GetBoardingHouseService getBoardingHouseService;

    @Host
    @MutationMapping
    public String createBoardingRoom(
            @Argument("createBoardingRoomInput") @Valid CreateBoardingRoomRequest createBoardingRoomRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        createBoardingHouseService.createBoardingRoom(nuriUserDetails, createBoardingRoomRequest);
        return "하숙방을 추가하였습니다";
    }

    @Host
    @QueryMapping
    public BoardingHouseInfo getMyBoardingHouse(
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getBoardingHouseService.getMyBoardingHouse(nuriUserDetails);
    }

    @Host
    @QueryMapping
    public List<BoardingRoomAndBoardersInfo> getBoardingRoomAndBoardersInfoList(
            @Argument("houseId") UUID houseId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {

        return houseId != null
                ? getBoardingHouseService.getBoardingRoomAndBoardersInfo(houseId)
                : getBoardingHouseService.getBoardingRoomAndBoardersInfo(nuriUserDetails);
    }
}
