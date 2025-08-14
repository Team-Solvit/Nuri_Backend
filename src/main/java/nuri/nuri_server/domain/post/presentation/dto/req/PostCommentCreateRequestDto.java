package nuri.nuri_server.domain.post.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PostCommentCreateRequestDto(
        @NotNull(message = "댓글 달기 시 게시물 아이디(postId)는 필수 항목입니다.")
        UUID postId,

        @NotBlank(message = "댓글 달기 시 내용(contents)은 필수 항목입니다.")
        String contents
) {}
