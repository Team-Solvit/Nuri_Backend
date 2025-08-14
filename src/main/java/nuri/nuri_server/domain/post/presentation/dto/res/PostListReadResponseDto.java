package nuri.nuri_server.domain.post.presentation.dto.res;

import lombok.Builder;
import nuri.nuri_server.domain.post.presentation.dto.common.PostInfo;
import nuri.nuri_server.domain.post.presentation.dto.common.PostType;

@Builder
public record PostListReadResponseDto(
        PostType postType,
        PostInfo postInfo
) {}
