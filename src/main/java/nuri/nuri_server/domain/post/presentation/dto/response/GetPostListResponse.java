package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;
import nuri.nuri_server.domain.post.presentation.dto.PostListInfo;
import nuri.nuri_server.domain.post.presentation.dto.PostType;

@Builder
public record GetPostListResponse(
        PostType postType,
        PostListInfo postInfo
) {}
