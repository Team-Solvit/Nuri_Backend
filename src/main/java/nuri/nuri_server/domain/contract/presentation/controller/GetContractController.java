package nuri.nuri_server.domain.contract.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.contract.application.service.GetContractService;
import nuri.nuri_server.domain.contract.presentation.dto.common.RoomContractDto;
import nuri.nuri_server.global.security.annotation.BoardingAuthUsers;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class GetContractController {

    private final GetContractService getContractService;

    @BoardingAuthUsers
    @QueryMapping
    public RoomContractDto getRoomContractList(
            @Argument("roomId") @NotNull(message = "방 계약 조회시 방 아이디(roomId)는 필수 항목입니다.")UUID roomId
    ) {
        return getContractService.getRoomContract(roomId);
    }
}
