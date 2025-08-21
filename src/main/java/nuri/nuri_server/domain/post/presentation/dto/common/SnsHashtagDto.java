package nuri.nuri_server.domain.post.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;

import java.util.UUID;

@Builder
public record SnsHashtagDto(
        UUID hashtagId,
        UUID postId,
        String name
) {
    public static SnsHashtagDto from(HashTagEntity hashtag) {
        return SnsHashtagDto.builder()
                .hashtagId(hashtag.getId())
                .postId(hashtag.getPost().getId())
                .name(hashtag.getName())
                .build();
    }
}
