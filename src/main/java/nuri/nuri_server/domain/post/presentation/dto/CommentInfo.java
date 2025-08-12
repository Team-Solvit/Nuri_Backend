package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostCommentEntity;
import nuri.nuri_server.domain.user.presentation.dto.UserInfo;

import java.util.UUID;

@Builder
public record CommentInfo(
        UUID commentId,
        UUID postId,
        UserInfo commenter,
        String content
) {
        public static CommentInfo from(PostCommentEntity comment) {
                UserInfo commenter = UserInfo.from(comment.getUser());
                return CommentInfo.builder()
                        .commentId(comment.getId())
                        .postId(comment.getPost().getId())
                        .commenter(commenter)
                        .content(comment.getContents())
                        .build();
        }
}