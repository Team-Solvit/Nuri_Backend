package nuri.nuri_server.domain.post.presentation.dto.res;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PostThumbnailGetResponseDto(
        UUID postId,
        String thumbnail
) {}
