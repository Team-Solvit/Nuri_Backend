package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PostThumbnailInfo(
        UUID postId,
        String thumbnail
) {}
