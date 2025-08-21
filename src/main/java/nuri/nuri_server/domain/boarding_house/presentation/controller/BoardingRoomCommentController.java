package nuri.nuri_server.domain.boarding_house.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nuri.nuri_server.domain.boarding_house.application.service.BoardingRoomCommentService;
import nuri.nuri_server.domain.boarding_house.presentation.dto.common.BoardingRoomCommentDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomCommentCreateRequestDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomCommentListReadRequestDto;
import nuri.nuri_server.domain.boarding_house.presentation.dto.req.BoardingRoomCommentUpdateRequestDto;
import nuri.nuri_server.global.security.annotation.User;
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
public class BoardingRoomCommentController {

    private final BoardingRoomCommentService boardingRoomCommentService;

    @User
    @MutationMapping
    public String createBoardingRoomComment(
            @Argument("boardingRoomCommentCreateInput") @Valid BoardingRoomCommentCreateRequestDto boardingRoomCommentCreateRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomCommentService.createComment(nuriUserDetails, boardingRoomCommentCreateRequestDto);
        return "하숙방 댓글을 작성하였습니다.";
    }

    @QueryMapping
    public List<BoardingRoomCommentDto> getBoardingRoomCommentList(
            @Argument("boardingRoomCommentListReadInput") @Valid BoardingRoomCommentListReadRequestDto boardingRoomCommentListReadRequestDto
    ) {
        return boardingRoomCommentService.getCommentList(boardingRoomCommentListReadRequestDto);
    }

    @User
    @MutationMapping
    public String updateBoardingRoomComment(
            @Argument("boardingRoomCommentUpdateInput") @Valid BoardingRoomCommentUpdateRequestDto boardingRoomCommentUpdateRequestDto,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomCommentService.updateComment(nuriUserDetails, boardingRoomCommentUpdateRequestDto);
        return "하숙방 댓글을 수정하였습니다.";
    }

    @User
    @MutationMapping
    public String deleteBoardingRoomComment(
            @Argument("commentId")  @NotNull(message = "댓글 아이디(commentId)는 필수 항목입니다.") UUID commentId,
            @AuthenticationPrincipal NuriUserDetails nuriUserDetails
    ) {
        boardingRoomCommentService.deleteComment(nuriUserDetails, commentId);
        return "하숙방 댓글을 삭제하였습니다.";
    }
}
