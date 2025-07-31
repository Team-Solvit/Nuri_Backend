package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;
import nuri.nuri_server.domain.post.presentation.dto.AuthorInfo;
import nuri.nuri_server.domain.post.presentation.dto.CommentInfo;

@Builder
public record GetCommentResponse(
        CommentInfo comment,
        AuthorInfo commenter
) {}
