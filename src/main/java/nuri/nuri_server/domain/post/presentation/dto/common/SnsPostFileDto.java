package nuri.nuri_server.domain.post.presentation.dto.common;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;

import java.util.UUID;

@Builder
public record SnsPostFileDto(
        UUID fileId,
        UUID postId,
        String url
) {
    public static SnsPostFileDto from(PostFileEntity file) {
        return SnsPostFileDto.builder()
                .fileId(file.getId())
                .postId(file.getPost().getId())
                .url(file.getMediaUrl())
                .build();
    }
}
