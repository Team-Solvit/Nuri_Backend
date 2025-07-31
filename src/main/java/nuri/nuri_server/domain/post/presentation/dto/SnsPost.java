package nuri.nuri_server.domain.post.presentation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record SnsPost(
        PostType type,
        UUID postId,
        List<String> files,
        String title,
        String contents,
        Long likes,
        Long comments,
        LocalDate day,
        List<String> hashtags
) implements PostListInfo {}
