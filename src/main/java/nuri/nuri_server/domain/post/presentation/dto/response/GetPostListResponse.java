package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;

@Builder
public record GetPostListResponse(
        PostListInfo post,
        AuthorInfo author
) {}
