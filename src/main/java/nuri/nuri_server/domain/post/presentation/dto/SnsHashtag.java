package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.HashTagEntity;

import java.util.UUID;

@Builder
public record SnsHashtag(
        UUID hashtagId,
        UUID postId,
        String name
) {
    public static SnsHashtag from(HashTagEntity hashtag) {
        return SnsHashtag.builder()
                .hashtagId(hashtag.getId())
                .postId(hashtag.getPost().getId())
                .name(hashtag.getName())
                .build();
    }
}
