package nuri.nuri_server.domain.post.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentInfo(
        @NotNull(message = "댓글 정보에는 게시물 아이디(postId)가 필수 항목입니다.")
        UUID commentId,

        @NotBlank(message = "댓글 정보에는 내용(contents)이 필수 항목입니다.")
        String content
) {}
