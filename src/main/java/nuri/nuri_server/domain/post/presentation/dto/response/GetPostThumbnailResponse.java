package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GetPostThumbnailResponse(
        UUID postId,
        String thumbnail
) {}
