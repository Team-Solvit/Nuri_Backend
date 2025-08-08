package nuri.nuri_server.domain.boarding_house.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.CreateBoardingHouseService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.CreateBoardingRoomRequest;
import nuri.nuri_server.global.security.annotation.Host;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BoardingHouseController {

    private final CreateBoardingHouseService createBoardingHouseService;

    @Host
    @MutationMapping
    public String createBoardingRoom(
            @Argument("createBoardingRoomInput") @Valid CreateBoardingRoomRequest createBoardingRoomRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        createBoardingHouseService.createBoardingRoom(nuriUserDetails, createBoardingRoomRequest);
        return "하숙방을 추가하였습니다";
    }
}
