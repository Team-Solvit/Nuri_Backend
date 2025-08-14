package nuri.nuri_server.domain.post.presentation.dto.req;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PostCommentListReadRequestDto(
        @NotNull(message = "댓글 조회시 시작위치(start)는 필수 항목입니다.")
        Integer start,

        @NotNull(message = "댓글 조회시 게시물 아이디(postId)는 필수 항목입니다.")
        UUID postId
) {}
