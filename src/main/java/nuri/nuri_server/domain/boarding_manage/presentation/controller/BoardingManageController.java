package nuri.nuri_server.domain.boarding_manage.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import nuri.nuri_server.domain.boarding_manage.application.service.GetBoardingManageService;
import nuri.nuri_server.global.security.annotation.ThirdParty;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardingManageController {

    private final GetBoardingManageService getBoardingManageService;

    @ThirdParty
    @QueryMapping
    public List<BoardingHouseDto> getManageBoardingHouseList(
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getBoardingManageService.getManageBoardingHouseList(nuriUserDetails);
    }

    @ThirdParty
    @QueryMapping
    public List<RoomContractDto> getManageBoardingRoomList(
            @Argument("houseId") @NotNull(message = "관리 하숙방 조회시 하숙집 아이디(houseId)는 필수 항목입니다.")UUID houseId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getBoardingManageService.getManageBoardingRoomList(nuriUserDetails, houseId);
    }
}
