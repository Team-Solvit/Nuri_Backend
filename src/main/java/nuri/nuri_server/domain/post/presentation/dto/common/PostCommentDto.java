package nuri.nuri_server.domain.post.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostCommentEntity;
import nuri.nuri_server.domain.user.presentation.dto.common.UserDto;

import java.util.UUID;

@Builder
public record PostCommentDto(
        UUID commentId,
        UUID postId,
        UserDto commenter,
        String content
) {
        public static PostCommentDto from(PostCommentEntity comment) {
                UserDto commenter = UserDto.from(comment.getUser());
                return PostCommentDto.builder()
                        .commentId(comment.getId())
                        .postId(comment.getPost().getId())
                        .commenter(commenter)
                        .content(comment.getContents())
                        .build();
        }
}