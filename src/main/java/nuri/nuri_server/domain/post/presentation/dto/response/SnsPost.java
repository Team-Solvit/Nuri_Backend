package nuri.nuri_server.domain.post.presentation.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record SnsPost(
        PostType type,
        String postId,
        List<String> files,
        String title,
        String contents,
        Long likes,
        Long comments,
        LocalDate day,
        List<String> hashtags
) implements PostListInfo {}
