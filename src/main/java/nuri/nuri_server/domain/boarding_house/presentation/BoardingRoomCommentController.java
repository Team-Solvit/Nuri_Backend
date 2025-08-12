package nuri.nuri_server.domain.boarding_house.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomCommentService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.BoardingRoomCommentInfo;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.CreateBoardingRoomCommentRequest;
import nuri.nuri_server.domain.boarding_house.presentation.dto.request.GetBoardingRoomCommentListRequest;
import nuri.nuri_server.global.security.annotation.User;
import nuri.nuri_server.global.security.user.NuriUserDetails;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardingRoomCommentController {

    private final BoardingRoomCommentService boardingRoomCommentService;

    @User
    @MutationMapping
    public String createBoardingRoomComment(
            @Argument("createBoardingRoomInput") @Valid CreateBoardingRoomCommentRequest createBoardingRoomCommentRequest,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomCommentService.createComment(nuriUserDetails, createBoardingRoomCommentRequest);
        return "게시물 댓글을 작성하였습니다.";
    }

    @QueryMapping
    public List<BoardingRoomCommentInfo> getBoardingRoomCommentList(
            @Argument("getBoardingRoomCommentListInput") @Valid GetBoardingRoomCommentListRequest getBoardingRoomCommentListRequest
    ) {
        return boardingRoomCommentService.getCommentList(getBoardingRoomCommentListRequest);
    }
}
