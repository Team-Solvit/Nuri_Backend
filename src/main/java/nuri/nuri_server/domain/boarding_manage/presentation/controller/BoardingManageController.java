package nuri.nuri_server.domain.boarding_manage.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingHouseDto;
import nuri.nuri_server.domain.boarding_manage.application.service.WorkingBoardingManageService;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.common.BoardingManageWorkDto;
import nuri.nuri_server.domain.boarding_manage.presentation.dto.req.BoardingManageWorkReadRequestDto;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import nuri.nuri_server.domain.boarding_manage.application.service.GetBoardingManageService;
import nuri.nuri_server.global.security.annotation.ThirdParty;
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
public class BoardingManageController {

    private final GetBoardingManageService getBoardingManageService;
    private final WorkingBoardingManageService workingBoardingManageService;

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

    @ThirdParty
    @QueryMapping
    public List<BoardingManageWorkDto> getBoardingManageWork(
            @Argument("boardingManageWorkReadInput") @Valid BoardingManageWorkReadRequestDto boardingManageWorkReadRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        return getBoardingManageService.getBoardingManageWork(nuriUserDetails, boardingManageWorkReadRequestDto);
    }

    @ThirdParty
    @MutationMapping
    public String completeBoardingManageWork(
            @Argument("workId") @NotNull(message = "하숙관리 업무 완료시 업무 아이디(workId)는 필수 항목입니다.") UUID workId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        workingBoardingManageService.completeBoardingManageWork(nuriUserDetails, workId);

        return "하숙관리 업무를 완료 하였습니다.";
    }

    @ThirdParty
    @MutationMapping
    public String inCompleteBoardingManageWork(
            @Argument("workId") @NotNull(message = "하숙관리 업무 완료 취소시 업무 아이디(workId)는 필수 항목입니다.") UUID workId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        workingBoardingManageService.incompleteBoardingManageWork(nuriUserDetails, workId);

        return "하숙관리 업무 완료를 취소 하였습니다.";
    }
}
