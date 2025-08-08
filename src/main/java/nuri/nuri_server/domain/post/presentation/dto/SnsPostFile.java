package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;
import nuri.nuri_server.domain.post.domain.entity.PostFileEntity;

import java.util.UUID;

@Builder
public record SnsPostFile(
        UUID fileId,
        UUID postId,
        String url
) {
    public static SnsPostFile from(PostFileEntity file) {
        return SnsPostFile.builder()
                .fileId(file.getId())
                .postId(file.getPost().getId())
                .url(file.getMediaUrl())
                .build();
    }
}
