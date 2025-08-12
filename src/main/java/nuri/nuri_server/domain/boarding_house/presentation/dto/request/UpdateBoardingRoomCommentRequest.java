package nuri.nuri_server.domain.boarding_house.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateBoardingRoomCommentRequest(
        @NotNull(message = "댓글 수정 시 댓글 아이디(commentId)는 필수 항목입니다.")
        UUID commentId,

        @NotBlank(message = "댓글 수정 시 내용(contents)은 필수 항목입니다.")
        String content
) {}
