package nuri.nuri_server.domain.boarding_house.presentation.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomLikeService;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BoardingRoomLikeController {
    private final BoardingRoomLikeService boardingRoomLikeService;

    @User
    @MutationMapping
    public String likeRoom(
            @Argument("roomId") @NotNull(message = "하숙방 좋아요 생성시 하숙방 아이디(roomId)는 필수 항목입니다.") UUID roomId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomLikeService.like(roomId, nuriUserDetails);
        return "하숙방 좋아요를 눌렀습니다.";
    }

    @User
    @MutationMapping
    public String unlikeRoom(
            @Argument("roomId") @NotNull(message = "하숙방 좋아요 취소시 하숙방 아이디(roomId)는 필수 항목입니다.") UUID roomId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomLikeService.unlike(roomId, nuriUserDetails);
        return "하숙방 좋아요를 취소했습니다.";
    }
}
